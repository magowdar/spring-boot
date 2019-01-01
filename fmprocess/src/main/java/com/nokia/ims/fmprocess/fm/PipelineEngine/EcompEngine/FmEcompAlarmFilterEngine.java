package com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmRulesEngine;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FmEcompAlarmFilterEngine {
	static Logger logger = LogManager.getLogger(FmEcompAlarmFilterEngine.class.getName());
	static int ecompIdCurrent = 1;
	int ecompIdOld ;
	long eventTimeOld;
	public void eventFilterCheck(FmEvent event) throws Exception {

		List<FmEvent> historicalEventList = null;
			historicalEventList = getAlarmByIdAndParams(event);
			if (historicalEventList != null) {
				eventTimeOld = historicalEventList.get(0).EVENT_TIME;
				event.SEQUENCE_ID = historicalEventList.get(0).SEQUENCE_ID;
				ecompIdOld = historicalEventList.get(0).ECOMP_EVENT_ID;
				event.setEcompEventParams(ecompIdOld, historicalEventList.get(0).ECOMP_SEQUENCE_ID + 1, eventTimeOld);
				event.CHANGED = true;
				logger.info("EcompId patched " + ecompIdOld + " SeqId patched "
						+ (historicalEventList.get(0).ECOMP_SEQUENCE_ID + 1));
			} else {
				String searchQuery = "SELECT *  FROM "	+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE
						 //+ " where eventId IN ( '"+ event.EVENT_ID + "')
						 + " order by eventTime Desc limit 1 ";
				ArrayList<FmEvent> historicalEvent = FmHsqlDBReady.searchForEcompEvents(searchQuery);
				logger.info("searchQuery based on eventId = "+searchQuery);
				if (historicalEvent != null && historicalEvent.size() > 0 ) {
					ecompIdOld = historicalEvent.get(0).ECOMP_EVENT_ID + 1;
					eventTimeOld = event.EVENT_TIME;
					event.setEcompEventParams(ecompIdOld, 1, eventTimeOld);
					logger.info("EcompId patched1Id " + ecompIdOld);
				} else {
					ecompIdOld = ecompIdCurrent++;
					eventTimeOld = event.EVENT_TIME;
					event.setEcompEventParams(ecompIdOld, 1, eventTimeOld);
					logger.info("EcompId patched2Id " + ecompIdOld);
				}
			}
	}

	private List<FmEvent> getAlarmByIdAndParams(FmEvent event) throws Exception {

		String searchQuery = FmRulesEngine.ddrRuleList.get(event.SPECIFIC_PROBLEM);
		if (searchQuery != null && !searchQuery.isEmpty()) {
			searchQuery = event.editQuery(searchQuery);
			searchQuery = searchQuery + " order by eventTime Desc limit 1";
			logger.info("Search query to find matching events is " + searchQuery);
			ArrayList<FmEvent> historicalEvent = FmHsqlDBReady.searchForEcompEvents(searchQuery);
			if (historicalEvent != null) {
				logger.info("List of matching events size is " + historicalEvent.size());
				if (!historicalEvent.isEmpty()) {
					return historicalEvent;
				}
			}
		}
		return null;
	}
}

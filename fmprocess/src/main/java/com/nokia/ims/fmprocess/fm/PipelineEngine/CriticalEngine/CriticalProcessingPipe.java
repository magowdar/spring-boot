package com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmRulesEngine;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmSequenceNumberHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CustomPipelineStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmAlarmFilterEngine;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/*This Stage is used for alarm processing operations like filtering and clearing for critical alarms*/
public class CriticalProcessingPipe extends CustomPipelineStage {

	static Logger logger = LogManager.getLogger(CriticalProcessingPipe.class.getName());
	FmAlarmFilterEngine alarmFilterEngine = new FmAlarmFilterEngine();

	public void run() {
		while (true) {
			try {
				FmEvent alarm = read();
				Long seqId = alarm.SEQUENCE_ID;
				FmSequenceNumberHandler.toBeSyncedSeqId = seqId;
				if (alarm.EVENT_SEVERITY != 6) {
					if (!alarmFilterEngine.eventFilterCheck(alarm)) {
							alarm.writeToHistoryTable();
							FmContext.getCriticalConverterRef().write(alarm);
							logger.debug("Alarm Sent to ConverterStage is " + alarm.SEQUENCE_ID
									+ " original Event SeqId " + seqId);
					}
				} else {
					logger.info("Started clear process for " + alarm.SEQUENCE_ID);
					ArrayList<FmEvent> listofClearAlarms = clearAlarms(alarm);
					logger.info("Clear event list size " + listofClearAlarms.size());
					for (FmEvent event : listofClearAlarms) {
						event.EVENT_CLEAR_USER = "NE system";
						event.EVENT_TIME = alarm.EVENT_TIME;
						FmContext.getCriticalConverterRef().write(event);
						event.deleteFromHistoryTable();
						logger.info("ClearEvent Sent to ConverterStage " + event.SEQUENCE_ID);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	public ArrayList<FmEvent> clearAlarms(FmEvent clearEvent) {

		ArrayList<FmEvent> alarmsToBeCleared = new ArrayList<FmEvent>();
		try {
			ArrayList<String> searchQueryList = FmRulesEngine.ecrRuleList.get(clearEvent.SPECIFIC_PROBLEM);
			logger.info("Number of entries in correlation table for this clear event " + searchQueryList.size());
			for (String searchQuery : searchQueryList) {
				if (searchQuery != null && !searchQuery.isEmpty()) {
					logger.info("searchQuery " + searchQuery);
					ArrayList<FmEvent> alarmsToBeCheckedForClear = new ArrayList<FmEvent>();
					String[] searchQueryArray = clearEvent.editQuery(searchQuery).split("#");
					logger.info("Now working for Search query " + searchQueryArray[0]);
					searchQueryArray[0] = searchQueryArray[0] + " AND eventSeverity=1";
					alarmsToBeCheckedForClear.addAll(FmHsqlDBReady.searchForEvents(searchQueryArray[0]));
					logger.info("Size of toBeCheckedforClear temp list is " + alarmsToBeCheckedForClear.size());
					if (searchQueryArray.length == 2) {
						String[] clearEventparams = null;
						String[] toBeClearedEventParams = null;
						clearEventparams = clearEvent.EVENT_PARAMETER.split("\\^0");
						for (FmEvent toBeClearedEvent : alarmsToBeCheckedForClear) {
							logger.info("Working for second part of the query");
							toBeClearedEventParams = null;
							toBeClearedEventParams = toBeClearedEvent.EVENT_PARAMETER.split("\\^0");

							int index = 0;
							Character character = searchQueryArray[1].charAt(index);
							try {
								boolean status = true;
								while (!character.equals(';')) {
									int abateIndex = Integer.parseInt(character.toString());
									int paramIndex = -1;
									Integer intValue = null;
									char operator = searchQueryArray[1].charAt(++index);
									character = searchQueryArray[1].charAt(++index);
									if (Character.isDigit(character)) {
										Character nextchar = searchQueryArray[1].charAt(index + 1);
										String token = character.toString();
										while (Character.isDigit(nextchar)) {
											token = token + nextchar;
											index++;
											nextchar = searchQueryArray[1].charAt(index + 1);
										}
										intValue = new Integer(token);
										logger.info("Value in partial string considered for comparison is " + intValue);
										index = index + 1;
									} else {
										character = searchQueryArray[1].charAt(++index);
										paramIndex = Integer.parseInt(character.toString());
										index = index + 2;
									}
									int param2 = intValue != null ? intValue
											: Integer.parseInt(toBeClearedEventParams[paramIndex].split("=")[1]);
									int param1 = Integer.parseInt(clearEventparams[abateIndex].split("=")[1]);
									logger.info("clearparam " + param1 + " and raisedparam " + param2);
									status = status && compareParams(operator, param1, param2);
									logger.info("Status of comparison is " + status);
									character = searchQueryArray[1].charAt(index);
									intValue = null;
									paramIndex = -1;
								}
								if (status) {
									logger.info("Based on status adding the clearing alarms to the List");
									alarmsToBeCleared.add(toBeClearedEvent);
								}
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}
					} else {
						alarmsToBeCleared.addAll(alarmsToBeCheckedForClear);
						logger.info("Adding all the clearing alarms to List");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return alarmsToBeCleared;
	}

	private boolean compareParams(char operator, int param1, int param2) {
		switch (operator) {
		case '>':
			return param1 > param2;
		case 'G':
		case 'g':
			return param1 >= param2;
		case '<':
			return param1 < param2;
		case 'L':
		case 'l':
			return param1 <= param2;
		}
		return false;
	}
}

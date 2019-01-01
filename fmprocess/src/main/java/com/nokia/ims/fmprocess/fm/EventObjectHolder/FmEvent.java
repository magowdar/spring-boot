package com.nokia.ims.fmprocess.fm.EventObjectHolder;

import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmRulesEngine;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class FmEvent {

	public static Logger logger = LogManager.getLogger(FmEvent.class.getName());

	public long SEQUENCE_ID;
	public int ECOMP_SEQUENCE_ID;
	public int EVENT_TYPE;
	public int EVENT_SEVERITY;
	public int SPECIFIC_PROBLEM;
	public long EVENT_TIME;
	public long ECOMP_EVENT_TIME;

	public String EVENT_ID;
	public int ECOMP_EVENT_ID;
	public String EVENT_CE_NAME;
	public String EVENT_PROCESS_NAME;
	public String EVENT_SHORT_TEXT;
	public String EVENT_PARAMETER;
	public String EVENT_FAULTY_OBJECT;
	public String EVENT_CLEAR_USER;
	public boolean CHANGED = false;

	public FmEvent(FmEvent readEvent) {

		this.SEQUENCE_ID = readEvent.SEQUENCE_ID;
		this.SPECIFIC_PROBLEM = readEvent.SPECIFIC_PROBLEM;
		this.EVENT_TYPE = readEvent.EVENT_TYPE;
		this.EVENT_SEVERITY = readEvent.EVENT_SEVERITY;
		this.ECOMP_EVENT_TIME = readEvent.EVENT_TIME;
		this.EVENT_TIME = readEvent.EVENT_TIME;
		this.EVENT_ID = readEvent.EVENT_ID;
		this.EVENT_CE_NAME = readEvent.EVENT_CE_NAME;
		this.EVENT_PROCESS_NAME = readEvent.EVENT_PROCESS_NAME;
		this.EVENT_SHORT_TEXT = readEvent.EVENT_SHORT_TEXT;
		this.EVENT_PARAMETER = readEvent.EVENT_PARAMETER;
		this.EVENT_FAULTY_OBJECT = readEvent.EVENT_FAULTY_OBJECT;
		this.EVENT_CLEAR_USER = readEvent.EVENT_CLEAR_USER;
	}

	public FmEvent() {

	}
	
	public void setEcompEventParams(int ecompId, int ecompSeqId, long ecompEventTime) {
		this.ECOMP_EVENT_ID = ecompId;
		this.ECOMP_SEQUENCE_ID = ecompSeqId;
		this.ECOMP_EVENT_TIME = ecompEventTime;
	}

	public String editQuery(String searchQuery) {

		Integer severity = EVENT_SEVERITY;
		if (searchQuery != null) {
			searchQuery = searchQuery.replaceAll("@pc", EVENT_PROCESS_NAME);
			searchQuery = searchQuery.replaceAll("@sv", severity.toString());
			searchQuery = searchQuery.replace("@ce", EVENT_CE_NAME);
			searchQuery = searchQuery.replace("@te", "" + EVENT_TIME);
			searchQuery = searchQuery.replace("@ep", EVENT_PARAMETER);
			
			int i = 0;
			String[] params = null;
			params = EVENT_PARAMETER.split("\\^0");
			while (searchQuery.contains("$")) {
				if (searchQuery.contains("$" + i))
					searchQuery = searchQuery.replace("$" + i,
							params[i].substring(2));
				i++;
			}
			searchQuery = searchQuery.replace("\"", "\'");
		}
		logger.info("event search query " + searchQuery);
		return searchQuery;
	}

	public String getDDRCorrelationInfo() {
		return FmRulesEngine.ddrRuleList.get(this.SPECIFIC_PROBLEM);
	}

	public ArrayList<String> getECRCorrelationInfo() {
		return FmRulesEngine.ecrRuleList.get(this.SPECIFIC_PROBLEM);
	}

	public String prepInsertQuery(String tableName) {
		String insertQuery = "Insert into " + tableName + " values ( "
				+ this.SEQUENCE_ID + "," + this.SPECIFIC_PROBLEM + ","
				+ this.EVENT_TYPE + "," + this.EVENT_SEVERITY + ","
				+ this.EVENT_TIME + ",'" + this.EVENT_ID + "','"
				+ this.EVENT_CE_NAME + "','" + this.EVENT_PROCESS_NAME + "','"
				+ this.EVENT_SHORT_TEXT.replaceAll("'", "") + "','"
				+ this.EVENT_PARAMETER.replaceAll("'", "") + "','" + this.EVENT_FAULTY_OBJECT
				+ "' );";
		return insertQuery;
	}

	public void writeToHistoryTable() {
		String query = "";
		if (this.CHANGED) {
			query = "Update " + FmHsqlDBUtil.HISTORY_EVENTS_TABLE
					+ " set eventTime = " + this.EVENT_TIME
					+ " where sequenceId=" + this.SEQUENCE_ID + ";";
		} else {
			query = prepInsertQuery(FmHsqlDBUtil.HISTORY_EVENTS_TABLE);
		}
		logger.info("query executed " + query);
		FmHsqlDBReady.statementExecute(query);
	}

	public void deleteFromHistoryTable() {
		String deleteQuery = "delete from " + FmHsqlDBUtil.HISTORY_EVENTS_TABLE
				+ " where sequenceId =" + this.SEQUENCE_ID;
		FmHsqlDBReady.statementExecute(deleteQuery);
	}
}

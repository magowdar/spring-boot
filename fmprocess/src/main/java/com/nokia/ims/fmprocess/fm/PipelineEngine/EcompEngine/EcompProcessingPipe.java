package com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmRulesEngine;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmSequenceNumberHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmContext;
import com.nokia.ims.fmprocess.fm.PipelineEngine.PipelineStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/*This Stage is used for alarm processing operations like filtering and clearing for major alarms*/
public class EcompProcessingPipe extends PipelineStage {

	static Logger logger = LogManager.getLogger(EcompProcessingPipe.class.getName());
	FmEcompAlarmFilterEngine alarmFilterEngine = new FmEcompAlarmFilterEngine();

	public void run() {
		while (true) {
			try {
				FmEvent alarm = read();
				Long seqId = alarm.SEQUENCE_ID;
				FmSequenceNumberHandler.toBeSyncedSeqId = seqId;
				if (alarm.EVENT_SEVERITY != 6) {
					alarmFilterEngine.eventFilterCheck(alarm);
					writeToHistoryTable(alarm);
					FmContext.getEcompConverterRef().write(alarm);
					logger.info("Alarm Sent to ConverterStage is " + alarm.SEQUENCE_ID);
				} else {
					logger.info("Started clear process for " + alarm.SEQUENCE_ID + " " + alarm.EVENT_ID);
					ArrayList<FmEvent> listofClearAlarms = clearAlarms(alarm);
					logger.info("Clear event list size " + listofClearAlarms.size());
					for (FmEvent event : listofClearAlarms) {
						event.EVENT_CLEAR_USER = "NE system";
						event.EVENT_TIME = alarm.ECOMP_EVENT_TIME;
						event.EVENT_SEVERITY = 6;
						event.ECOMP_SEQUENCE_ID = event.ECOMP_SEQUENCE_ID + 1;
						FmContext.getEcompConverterRef().write(event);
						deleteFromHistoryTable(event);
						logger.info("ClearEvent Sent to ConverterStage " + event.SEQUENCE_ID);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				if(e instanceof ArrayIndexOutOfBoundsException){
					logger.error("Improper alarm Parameters");
				}
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
					alarmsToBeCheckedForClear.addAll(FmHsqlDBReady.searchForEcompEvents(searchQueryArray[0]));
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

	private void deleteFromHistoryTable(FmEvent event) {
		String deleteQuery = "delete from " + FmHsqlDBUtil.HISTORY_EVENTS_TABLE + " where sequenceId ="
				+ event.SEQUENCE_ID;
		FmHsqlDBReady.statementExecute(deleteQuery);
	}

	private void writeToHistoryTable(FmEvent alarm) {
		String query = "";
		if (alarm.CHANGED) {
			query = "Update " + FmHsqlDBUtil.HISTORY_EVENTS_TABLE + " set ecompSequenceId = " + alarm.ECOMP_SEQUENCE_ID
					+ " where sequenceId=" + alarm.SEQUENCE_ID + ";";
		} else {
			query = prepInsertQuery(FmHsqlDBUtil.HISTORY_EVENTS_TABLE, alarm);
		}
		logger.info("query executed " + query);
		FmHsqlDBReady.statementExecute(query);

	}

	public String prepInsertQuery(String tableName, FmEvent alarm) {
		String insertQuery = "Insert into " + tableName + " values ( " + alarm.SEQUENCE_ID + ","
				+ alarm.SPECIFIC_PROBLEM + "," + alarm.EVENT_TYPE + "," + alarm.EVENT_SEVERITY + ","
				+ alarm.ECOMP_EVENT_TIME + "," + alarm.EVENT_TIME + ",'" + alarm.EVENT_ID + "','" + alarm.EVENT_CE_NAME
				+ "','" + alarm.EVENT_PROCESS_NAME + "','" + alarm.EVENT_SHORT_TEXT.replaceAll("'", "") + "','"
				+ alarm.EVENT_PARAMETER.replaceAll("'", "") + "','" + alarm.EVENT_FAULTY_OBJECT + "',"
				+ alarm.ECOMP_EVENT_ID + ", " + alarm.ECOMP_SEQUENCE_ID + " );";
		return insertQuery;
	}

}

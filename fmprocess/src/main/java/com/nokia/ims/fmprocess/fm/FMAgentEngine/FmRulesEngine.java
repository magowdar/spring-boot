package com.nokia.ims.fmprocess.fm.FMAgentEngine;

//import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FileNameFilter;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class FmRulesEngine {
	static Logger logger = LogManager.getLogger(FmRulesEngine.class.getName());

	public static HashMap<Integer, ArrayList<String>> ecrRuleList = new HashMap<Integer, ArrayList<String>>();
	public static HashMap<Integer, String> ddrRuleList = new HashMap<Integer, String>();

	public static HashMap<Integer, String> dropDuplicateRuleList = new HashMap<Integer, String>();
	private ArrayList<File> ruleFileNameList = new ArrayList<File>();

	private String sqlQuery;
	private String partialString;

	private final int ABATE_IDENTIFIER = 1;
	private final int ALARM_IDENTIFIER = 2;

	private String[] variables = new String[2];

	public void processCorrelationRules(String ecrExt, String ddrExt,
			String... pathToRules) {
		for (String path : pathToRules)
			listFilteredFiles(path, ecrExt, ddrExt);
		readCorrelationFiles();
	}

	private void listFilteredFiles(String ruleDir, String ecrExt, String ddrExt) {
		if (ruleDir != null) {
			File targetDir = new File(ruleDir);
			FileNameFilter selectCriteria = new FileNameFilter(ecrExt, ddrExt);
			if (targetDir.isDirectory()) {
				for (File eachFilteredFile : targetDir
						.listFiles(selectCriteria)) {
					ruleFileNameList.add(eachFilteredFile);
				}
			} else {
				logger.warn("Input path is not a directory ");
			}
		} else {
			logger.warn("Rule directory is null");
		}
	}

	private void readCorrelationFiles() {
		for (File file : ruleFileNameList) {
			parseFile(file);
		}
	}

	public void parseFile(File filename) {
		logger.info("Reading filename "+filename);
		String inputLine = null;
		String ecrRule = null;
		String alarmList = null;
		try (BufferedReader in = new BufferedReader(new FileReader(
				filename.getAbsolutePath()))) {
			while ((inputLine = in.readLine()) != null) {
				inputLine = inputLine.trim();
				if (!inputLine.isEmpty() && inputLine.charAt(0) == '['
						&& inputLine.endsWith("]")) {
					inputLine = inputLine.substring(1, inputLine.length() - 1);
					inputLine = inputLine.trim();
					String regex = "[ \t]+";
					String[] ecrParts = inputLine.split(regex);
					int eventAlarmId = FmUtil.getAlarmSpecificId(ecrParts[0]);
					if (eventAlarmId != 0) {
						if (ecrParts.length > 2) {
							/*below part of code checks  the severity of raised alarm corresponding to a clear alarm
							 * 	and puts it in the appropriate list. This list is used to check which pipe to send a particular
							 * clear alarm
							 */
							alarmList = ecrParts[1];
							String[] tempAlarmList = alarmList.split(",");
							for (String tempAlarmId: tempAlarmList){

								if(FmEDSEngine.criticalEventList.contains(tempAlarmId)){
									logger.info("clear " +ecrParts[0] + " found for critical " + tempAlarmId);
									ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearCriticalEventList.get(ecrParts[0]);
									if (listOfCriticalEvents != null)
										listOfCriticalEvents.add(tempAlarmId);
									else {
										ArrayList<String> eventList = new ArrayList<String>();
										eventList.add(tempAlarmId);
										FmEDSEngine.clearCriticalEventList.put(ecrParts[0], eventList);
									}
								} if (FmEDSEngine.majorEventList.contains(tempAlarmId)){
									logger.info("clear " +ecrParts[0] + " found for major " + tempAlarmId);
									ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearMajorEventList.get(ecrParts[0]);
									if (listOfCriticalEvents != null)
										listOfCriticalEvents.add(tempAlarmId);
									else {
										ArrayList<String> eventList = new ArrayList<String>();
										eventList.add(tempAlarmId);
										FmEDSEngine.clearMajorEventList.put(ecrParts[0], eventList);
									}
								} if (FmEDSEngine.minorEventList.contains(tempAlarmId)){
									logger.info("clear " +ecrParts[0] + " found for minor " + tempAlarmId);
									ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearMinorEventList.get(ecrParts[0]);
									if (listOfCriticalEvents != null)
										listOfCriticalEvents.add(tempAlarmId);
									else {
										ArrayList<String> eventList = new ArrayList<String>();
										eventList.add(tempAlarmId);
										FmEDSEngine.clearMinorEventList.put(ecrParts[0], eventList);
									}
								} if (FmEDSEngine.warningEventList.contains(tempAlarmId)){
									logger.info("clear " +ecrParts[0] + " found for warning " + tempAlarmId);
									ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearWarningEventList.get(ecrParts[0]);
									if (listOfCriticalEvents != null)
										listOfCriticalEvents.add(tempAlarmId);
									else {
										ArrayList<String> eventList = new ArrayList<String>();
										eventList.add(tempAlarmId);
										FmEDSEngine.clearWarningEventList.put(ecrParts[0], eventList);
									}
								}
							}
							ecrRule = patchRules(ecrParts[2]);
							prepDdrRuleEntry(ecrRule, alarmList, false, filename.getName());
							evaluateRule(ecrRule);
							alarmList = alarmList.replaceAll(",", "\",\"");
							sqlQuery = sqlQuery
									+ " eventTime <= @te AND eventId IN (\""
									+ alarmList + "\")";
							ArrayList<String> listOfQuery = ecrRuleList
									.get(eventAlarmId);
							if (listOfQuery != null)
								listOfQuery.add(sqlQuery);
							else {
								ArrayList<String> storeList = new ArrayList<String>();
								storeList.add(sqlQuery);
								ecrRuleList.put(eventAlarmId, storeList);
							}
							logger.info("ECR Rule Query "+sqlQuery);
						} else if (ecrParts.length == 2) {

							if (ecrParts[1].endsWith(";")) {
								ecrRule = patchRules(ecrParts[1]);
								
								prepDdrRuleEntry(ecrRule, ecrParts[0], true,filename.getName());
							} else {
								/*below part of code checks  the severity of raised alarm corresponding to a clear alarm
								 * 	and puts it in the appropriate list. This list is used to check which pipe to send a particular
								 * clear alarm
								 */
								alarmList = ecrParts[1];
								String[] tempAlarmList = alarmList.split(",");
								for (String tempAlarmId: tempAlarmList){
									/*String ddrSqlQuery = "SELECT *  FROM "
											+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE
											+ " where eventSeverity=@sv AND eventProcName=\"@pc\" AND eventParameter=\"@ep\" AND eventId IN (\""
											+ tempAlarmId + "\")";*/
									String ddrSqlQuery = "";
									int alarmId = FmUtil.getAlarmSpecificId(tempAlarmId);
									ddrRuleList.putIfAbsent(alarmId, ddrSqlQuery);
									logger.info("Replaced with default ddr query "+ ddrSqlQuery);
									
									if(FmEDSEngine.criticalEventList.contains(tempAlarmId)){
										logger.info("clear " +ecrParts[0] + " found for critical " + tempAlarmId);
										ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearCriticalEventList.get(ecrParts[0]);
										if (listOfCriticalEvents != null)
											listOfCriticalEvents.add(tempAlarmId);
										else {
											ArrayList<String> eventList = new ArrayList<String>();
											eventList.add(tempAlarmId);
											FmEDSEngine.clearCriticalEventList.put(ecrParts[0], eventList);
										}
									} if (FmEDSEngine.majorEventList.contains(tempAlarmId)){
										logger.info("clear " +ecrParts[0] + " found for major " + tempAlarmId);
										ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearMajorEventList.get(ecrParts[0]);
										if (listOfCriticalEvents != null)
											listOfCriticalEvents.add(tempAlarmId);
										else {
											ArrayList<String> eventList = new ArrayList<String>();
											eventList.add(tempAlarmId);
											FmEDSEngine.clearMajorEventList.put(ecrParts[0], eventList);
										}
									} if (FmEDSEngine.minorEventList.contains(tempAlarmId)){
										logger.info("clear " +ecrParts[0] + " found for minor " + tempAlarmId);
										ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearMinorEventList.get(ecrParts[0]);
										if (listOfCriticalEvents != null)
											listOfCriticalEvents.add(tempAlarmId);
										else {
											ArrayList<String> eventList = new ArrayList<String>();
											eventList.add(tempAlarmId);
											FmEDSEngine.clearMinorEventList.put(ecrParts[0], eventList);
										}
									} if (FmEDSEngine.warningEventList.contains(tempAlarmId)){
										logger.info("clear " +ecrParts[0] + " found for warning " + tempAlarmId);
										ArrayList<String> listOfCriticalEvents = FmEDSEngine.clearWarningEventList.get(ecrParts[0]);
										if (listOfCriticalEvents != null)
											listOfCriticalEvents.add(tempAlarmId);
										else {
											ArrayList<String> eventList = new ArrayList<String>();
											eventList.add(tempAlarmId);
											FmEDSEngine.clearWarningEventList.put(ecrParts[0], eventList);
										}
									}
								}
								alarmList = alarmList.replaceAll(",", "\",\"");
								/*sqlQuery = "SELECT *  FROM "
										+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE
										+ " where eventTime <= @te AND eventId IN (\""
										+ alarmList + "\")";*/
								sqlQuery = "";
								logger.info("Replaced with default ecr query "+ sqlQuery);
								ArrayList<String> listOfQuery = ecrRuleList
										.get(eventAlarmId);
								if (listOfQuery != null)
									listOfQuery.add(sqlQuery);
								else {
									ArrayList<String> storeList = new ArrayList<String>();
									storeList.add(sqlQuery);
									ecrRuleList.put(eventAlarmId, storeList);
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepDdrRuleEntry(String ecrRule, String alarmStringList,
			boolean forcedInsert,String filename) {
		logger.info("ddrSqlQuery query for the rule" + ecrRule+" : " +alarmStringList+" : "+ forcedInsert);
		String[] raisedAlarmList = alarmStringList.split(",");
		String ddrSqlQuery;
		ecrRule = ecrRule.replaceAll("(>|<|G|g|L|l|#)", "=");
		evaluateRule(ecrRule);
		for (String eachAlarm : raisedAlarmList) {
			ddrSqlQuery = sqlQuery + " eventSeverity=@sv AND eventId IN (\"" + eachAlarm + "\")";
			//logger.info("ddrSqlQuery query " + ddrSqlQuery);
			int alarmId = FmUtil.getAlarmSpecificId(eachAlarm);
			if(filename.endsWith( ".ddr" ))
				dropDuplicateRuleList.put( alarmId, ddrSqlQuery );
			if (forcedInsert) {
				ddrRuleList.put(alarmId, ddrSqlQuery);
			} else {
				ddrRuleList.putIfAbsent(alarmId, ddrSqlQuery);
			}
		}
	}

	public void evaluateRule(String correlationRule) {
		/*sqlQuery = "SELECT *  FROM " + FmHsqlDBUtil.HISTORY_EVENTS_TABLE
				+ " where ";*/
		sqlQuery = "";
		partialString = "";
		int abateIndex = 0;
		int errIndex = 0;
		Stack<Object> stack = new Stack<Object>();
		int startIndex = 0;
		Character character = correlationRule.charAt(startIndex);
		try {
			while (!character.equals(';')) {
				if (Character.isDigit(character)) {
					Character nextchar = correlationRule.charAt(startIndex + 1);
					String token = character.toString();
					while (Character.isDigit(nextchar)) {
						token = token + nextchar;
						startIndex++;
						nextchar = correlationRule.charAt(startIndex + 1);
					}
					Integer intValue = new Integer(token);// character.toString());
					stack.push(intValue);
				} else if (character.equals('"')) {
					int endIndex = correlationRule
							.indexOf("\"", startIndex + 1);
					String variable = correlationRule.substring(startIndex + 1,
							endIndex);
					stack.push(variable);
					startIndex = endIndex;
				} else if (character.equals('d') || character.equals('s')) {
					Object paramPos = stack.pop();
					Object paramSrc = stack.pop();
					int index = (Integer) paramPos - 1;
					Object paramNew = null;
					if ((Integer) paramSrc == ABATE_IDENTIFIER) {
						paramNew = "eventParameter";
						abateIndex = index;
					} else if ((Integer) paramSrc == ALARM_IDENTIFIER) {
						paramNew = "$" + abateIndex + "^";
						errIndex = index;
					} else {
						paramNew = variables[index];
					}
					if (paramNew != null) {
						stack.push(paramNew);
					}
				} else if ((character.toString()).matches("[=#&]?")) {
					Object rhs = stack.pop();
					Object lhs = stack.pop();
					Object temp, parNew = null;
					if (rhs.toString().equals("eventParameter")) {
						temp = rhs;
						rhs = lhs;
						lhs = temp;
					}
					parNew = compareEventParameter(character, lhs, rhs);
					stack.push(parNew);
				} else if (character.equals('V')) {
					String paramVar = (String) stack.pop();
					Object varPos = stack.pop();
					getVariableValue(paramVar, varPos);
				} else if ((character.toString()).matches("[>Gg<Ll]?")) {
					Object lhs = stack.pop();
					Object rhs = stack.pop();
					Object parNew = "ignore";
					stack.push(parNew);
					if (lhs.toString().contains("eventParameter")) {
						if (rhs.toString().contains("$"))
							rhs = "$" + errIndex + "^";
						partialString = partialString + abateIndex + character
								+ rhs.toString().replace("$", "@");
					} else {
						if (lhs.toString().contains("$"))
							lhs = "$" + errIndex + "^";
						partialString = partialString + abateIndex + character
								+ lhs.toString().replace("$", "@");
					}
				}
				startIndex++;
				character = correlationRule.charAt(startIndex);
			}
			sqlQuery = sqlQuery + (String) stack.pop() + " AND ";
		} catch (Exception exc) {
			logger.error(exc);
		}
	}

	private void getVariableValue(String strVar, Object varPos)
			throws Exception {
		int pos = (Integer) varPos - 1;
		if (strVar.equals("abce"))
			variables[pos] = "\"@ce\"";
		else if (strVar.equals("errce"))
			variables[pos] = "eventCeName";
		else if (strVar.equals("abproc"))
			variables[pos] = "\"@pc\"";
		else
			variables[pos] = "eventProcName";
	}

	private static String compareEventParameter(Character operator, Object lhs,
			Object rhs) {
		if (operator == '&') {
			if (lhs.toString().equals("ignore"))
				return (String) rhs;
			else if (rhs.toString().equals("ignore"))
				return (String) lhs;
			return lhs + " AND " + rhs;
		}
		if (lhs.toString().equals("eventParameter")) {
			if (operator.equals('='))
				return lhs + " LIKE " + "\"%=" + rhs + "%\"";
			else
				return lhs + " NOT LIKE " + "\"%=" + rhs + "%\"";
		} else if (operator.equals('='))
			return rhs + " = " + lhs;
		else
			return rhs + " <> " + lhs;
	}

	private static String patchRules(String ecRule) {
		String[] ruleParts = ecRule.split("=");
		ecRule = "";
		for (String eachRulePart : ruleParts) {
			if (eachRulePart.matches(".*[12],\"(abproc|errproc)\".*")) {
				if (!eachRulePart.contains("abproc")
						|| !eachRulePart.contains("errproc")) {
					eachRulePart = "1,\"abproc\"V2,\"errproc\"V3,1,s3,2,s";
				}
			} else if (eachRulePart.matches(".*[12],\"(abce|errce)\".*")) {
				if (!eachRulePart.contains("abce")
						|| !eachRulePart.contains("errce")) {
					eachRulePart = "1,\"abce\"V2,\"errce\"V3,1,s3,2,s";
				}
			}
			if (!eachRulePart.contains(";"))
				eachRulePart = eachRulePart + "=";
			ecRule = ecRule + eachRulePart;
		}
		ruleParts = null;
		return ecRule;
	}
}

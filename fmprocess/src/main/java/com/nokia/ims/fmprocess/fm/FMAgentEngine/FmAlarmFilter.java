package com.nokia.ims.fmprocess.fm.FMAgentEngine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class FmAlarmFilter {
	
	static Logger logger = LogManager.getLogger(FmAlarmFilter.class
			.getName());

	public boolean isActive;
	public boolean ignoreVariables;

	public int startSpecificProblem;
	public int endSpecificProblem;
	public int severity;
	public int timeInterval;

	public String uniqueRuleName;
	
	public static ArrayList<FmAlarmFilter> filterCollection;

	public FmAlarmFilter(boolean isActive, boolean ignoreVariables,
			int startSpecificProblem, int endSpecificProblem, int severity,
			int timeInterval, String uniqueRuleName) {
		this.isActive = isActive;
		this.ignoreVariables = ignoreVariables;
		this.startSpecificProblem = startSpecificProblem;
		this.endSpecificProblem = endSpecificProblem;
		this.severity = severity;
		this.timeInterval = timeInterval;
		this.uniqueRuleName = uniqueRuleName;
	}
	
	public FmAlarmFilter() {
		
	}
	
	public static void initAlarmFilterList(ArrayList<FmAlarmFilter> filterRules) {
		resetFilterCollection();
		if (filterRules.isEmpty()) {
			DefaultRules();
		} else {
			for (FmAlarmFilter rule : filterRules) {
				logger.info(" filterName = " + rule.uniqueRuleName
						+ " filterIsActive = " + rule.isActive
						+ " filterSeverity = " + rule.severity
						+ " filterTime = " + rule.timeInterval
						+ " filterStartId = " + rule.startSpecificProblem
						+ " filterEndId = " + rule.endSpecificProblem
						+ " filterIgnoreVar = " + rule.ignoreVariables);
				if (filterSettingsAreValid(rule)) {
					filterCollection.add(rule);
					logger.info("Adding the above Filter to Filter Collection");
				} else {
					logger.info("Above Filter is skipped from Filter Collection");
				}
			}
		}
	}
	
	private static boolean filterSettingsAreValid(FmAlarmFilter filter) {
		if (filter.isActive
				&& (filter.startSpecificProblem == -1 || (filter.startSpecificProblem > 0 && filter.endSpecificProblem >= filter.startSpecificProblem)))
			return true;
		return false;
	}

	private static void DefaultRules() {
		FmAlarmFilter dropDuplicateRule = new FmAlarmFilter(true, false, -1,
				-1, -1, 86400, "DropDuplicates");
		filterCollection.add(dropDuplicateRule);
		logger.info("Adding Default Alarm-Filter Rules to Filter Collection");
	}

	private static void resetFilterCollection() {

		if (filterCollection != null)
			filterCollection.clear();
		filterCollection = new ArrayList<FmAlarmFilter>();
		logger.info("Initialising Alarm-Filter Collection");
	}

	public String toString() {
		return "FmAlarmFilter: " + isActive + " " + ignoreVariables + " "
				+ startSpecificProblem + " " + endSpecificProblem + " "
				+ severity + " " + timeInterval + " " + uniqueRuleName;

	}
}

package com.nokia.ims.fmprocess.fm.MemDBEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FMAgent;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.jdbc.JDBCDataSource;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FmHsqlDBReady {

	private static Connection conn;
	static Logger logger = LogManager.getLogger(FmHsqlDBReady.class.getName());

	static {
		try {
			loadDriver();
			JDBCDataSource dataSource = new JDBCDataSource();
			if (FMAgent.outputType.equalsIgnoreCase("ECOMP"))
				dataSource.setDatabase(FmUtil.DataBasePathEcomp);
			else
				dataSource.setDatabase(FmUtil.DataBasePathEsymac);
			conn = dataSource.getConnection(FmUtil.DataBaseUser, FmUtil.DataBasePassword);
			conn.setAutoCommit(true);
		} catch (Exception e) {
			logger.error("Error during initial DB operations");
		}
	}

	private static void loadDriver() throws IllegalStateException {
		try {
			logger.info("Loading driver for hsqldb " + FmUtil.DataBaseDriver);
			Class.forName(FmUtil.DataBaseDriver);
		} catch (Exception e) {
			logger.error("Error in Loading HSQL DB Driver");
		}
	}

	public void createAlarmTables() throws SQLException {
		if ("ESYMAC".equalsIgnoreCase(FMAgent.outputType)) {
			if (statementExecute(FmHsqlDBUtil.CREATE_HISTORYEVENTS_TABLE) != 0)
				logger.warn("Failed to create History table.");
			else
				logger.info("Create table executed successfully ");
		} else {
			if (statementExecute(FmHsqlDBUtil.CREATE_ECOMP_HISTORYEVENTS_TABLE) != 0)
				logger.warn("Failed to create History table.");
			else
				logger.info("Create table executed successfully ");
		}
	}

	public static int statementExecute(String sqlString) {
		int status = 0;
		try (PreparedStatement stmt = conn.prepareStatement(sqlString)) {
			status = stmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("Failed to execute for SQL query " + sqlString);
		}
		return status;
	}

	public static ArrayList<FmEvent> searchForEvents(String query) {

		ArrayList<FmEvent> setOfEvents = new ArrayList<FmEvent>();
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			try (ResultSet rSet = stmt.executeQuery()) {
				while (rSet.next()) {

					FmEvent anEvent = new FmEvent();
					anEvent.SEQUENCE_ID = rSet.getInt(1);
					anEvent.SPECIFIC_PROBLEM = rSet.getInt(2);
					anEvent.EVENT_TYPE = rSet.getInt(3);
					anEvent.EVENT_SEVERITY = rSet.getInt(4);
					anEvent.EVENT_TIME = rSet.getLong(5);
					anEvent.EVENT_ID = rSet.getString(6);
					anEvent.EVENT_CE_NAME = rSet.getString(7);
					anEvent.EVENT_PROCESS_NAME = rSet.getString(8);
					anEvent.EVENT_SHORT_TEXT = rSet.getString(9);
					anEvent.EVENT_PARAMETER = rSet.getString(10);
					anEvent.EVENT_FAULTY_OBJECT = rSet.getString(11);
					setOfEvents.add(anEvent);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("Query is " + query);
		}
		return setOfEvents;
	}

	public static ArrayList<FmEvent> searchForEcompEvents(String searchQuery) {
		ArrayList<FmEvent> setOfEvents = new ArrayList<FmEvent>();
		try (PreparedStatement stmt = conn.prepareStatement(searchQuery)) {
			try (ResultSet rSet = stmt.executeQuery()) {
				while (rSet.next()) {

					FmEvent anEvent = new FmEvent();
					anEvent.SEQUENCE_ID = rSet.getInt(1);
					anEvent.SPECIFIC_PROBLEM = rSet.getInt(2);
					anEvent.EVENT_TYPE = rSet.getInt(3);
					anEvent.EVENT_SEVERITY = rSet.getInt(4);
					anEvent.ECOMP_EVENT_TIME = rSet.getLong(5);
					anEvent.EVENT_TIME = rSet.getLong(6);
					anEvent.EVENT_ID = rSet.getString(7);
					anEvent.EVENT_CE_NAME = rSet.getString(8);
					anEvent.EVENT_PROCESS_NAME = rSet.getString(9);
					anEvent.EVENT_SHORT_TEXT = rSet.getString(10);
					anEvent.EVENT_PARAMETER = rSet.getString(11);
					anEvent.EVENT_FAULTY_OBJECT = rSet.getString(12);
					anEvent.ECOMP_EVENT_ID = rSet.getInt(13);
					anEvent.ECOMP_SEQUENCE_ID = rSet.getInt(14);
					setOfEvents.add(anEvent);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("Query is " + searchQuery);
		}
		return setOfEvents;
	}
}

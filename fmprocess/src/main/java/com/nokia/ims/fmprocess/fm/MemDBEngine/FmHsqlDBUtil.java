package com.nokia.ims.fmprocess.fm.MemDBEngine;

public class FmHsqlDBUtil {

	public static String HISTORY_EVENTS_TABLE = "historyevents";
	
	public static final String CREATE_HISTORYEVENTS_TABLE = "CREATE MEMORY TABLE "
			+ HISTORY_EVENTS_TABLE
			+ " ( sequenceId	BIGINT PRIMARY KEY,"
			+ " specificProblem	INTEGER,"
			+ " eventType		INTEGER,"
			+ " eventSeverity	INTEGER,"
			+ " eventTime		BIGINT,"
			+ " eventId			VARCHAR(12),"
			+ " eventCeName		VARCHAR(64),"
			+ " eventProcName	VARCHAR(64),"
			+ " eventShortText	VARCHAR(1024),"
			+ " eventParameter	VARCHAR(1024),"
			+ " eventFaultyObject	VARCHAR(1024) );";
	
	public static final String CREATE_ECOMP_HISTORYEVENTS_TABLE = "CREATE TABLE "
			+ HISTORY_EVENTS_TABLE
			+ " ( sequenceId	BIGINT PRIMARY KEY,"
			+ " specificProblem	INTEGER,"
			+ " eventType		INTEGER,"
			+ " eventSeverity	INTEGER,"
			+ " eventTime		BIGINT,"
			+ " eventLastTime		BIGINT,"
			+ " eventId			VARCHAR(12),"
			+ " eventCeName		VARCHAR(64),"
			+ " eventProcName	VARCHAR(64),"
			+ " eventShortText	VARCHAR(1024),"
			+ " eventParameter	VARCHAR(1024),"
			+ " eventFaultyObject	VARCHAR(1024),"
			+ " ecompEventId	INTEGER, "
			+ " ecompSequenceId	INTEGER );";
}

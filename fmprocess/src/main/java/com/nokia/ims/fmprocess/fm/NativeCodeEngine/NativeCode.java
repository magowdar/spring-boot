package com.nokia.ims.fmprocess.fm.NativeCodeEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmAlarmFilter;

import java.util.ArrayList;
import java.util.HashMap;

public class NativeCode {

	static {
		try {
			System.loadLibrary("FmNativeCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public native static int registerToNodeManagerNative();

	public native static int registerForEventsNative(FmEvent singleTSPEvent);

	public native static int getFilterRulesFromNative(
			ArrayList<FmAlarmFilter> filterRules);

	public native static void getThresholdFromNative();

	public native static int getEcompTableFromNative(
			ArrayList<HashMap<String, String>> listOfEcompDomains);

	public native static void getEcompScalarParamFromNative();
	
	public native static int getEcompHBInterval();

	public native static int receiveSignalAndEventsFromTSPNative();

	public native static void recoverTSPEventRangeNative(
			ArrayList<FmEvent> EventList, long startSeqId, long endSeqId);

	public native static void recoverTSPEventListNative(
			ArrayList<FmEvent> esymacAlarmList, long[] seqList);

	public native static void recoverTSPEventBulkNative(
			ArrayList<FmEvent> EventList, long startSeqId, long endSeqId,
			int maxEvents);

	public native static int latestTSPSeqIdNative();

	public native static boolean authorizeUserNative(String userName,
			String operation, String service);

}

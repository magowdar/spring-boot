package com.nokia.ims.fmprocess.fm.EsymacManager;

import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
//import com.nokia.j2ssp.comp.esymac.api.AlarmClearCallbackIF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//public class EsymacClearCallbackHandler implements AlarmClearCallbackIF {
	public class EsymacClearCallbackHandler{


	static Logger logger = LogManager.getLogger(EsymacClearCallbackHandler.class.getName());
	
	public void clear(String sdnName, int specificProblem,
			String internalId, String clearUser) {
		logger.info("Received a callback from Esymac for Alarm "+internalId + " " + 
				specificProblem + " " + sdnName + " " + clearUser);
		String deleteQuery = "Delete from "
					+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE
					+ " where sequenceId = " + Long.parseLong(internalId);
		FmHsqlDBReady.statementExecute(deleteQuery);
		
		logger.info("Successfully deleted Alarm from History table. ");
	}
}
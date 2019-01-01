package com.nokia.ims.fmprocess.fm.EsymacManager;

import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
/*import com.nokia.j2ssp.comp.esymac.api.AlarmClearCallbackFilter;
import com.nokia.j2ssp.comp.esymac.util.EsymacConnection;*/
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetupEsymac {
	
	static Logger logger = LogManager.getLogger(SetupEsymac.class
			.getName());

	public static boolean setEsymacManager() throws InterruptedException {
		boolean success = false;
		/*int maxTries = FmUtil.MAX_TRIES;
		do {
			try {
				logger.debug("Properties " + FmUtil.EsymacDeployClassName);
				logger.debug("Properties " + FmUtil.EsymacDeployXML);
				System.setProperty(FmUtil.EsymacDeployClassName,
						FmUtil.EsymacDeployXML);
				FmUtil.EsymacGUIManager = EsymacConnection.getGUI()[0];
				FmUtil.EsymacFaultManager = EsymacConnection.getFaultManager();
				logger.info("Gui manager and fault manager "
						+ FmUtil.EsymacGUIManager + " "
						+ FmUtil.EsymacFaultManager);
				FmUtil.EsymacFaultManager.addAlarmClearCallback(
						new EsymacClearCallbackHandler(),
						new AlarmClearCallbackFilter());
				logger.info("ClearCallback to Esymac is success");
				EsymacConnection.getOMAgent().registerAuthorizationCallback(
						new Authorizator(), "ESYMAC_FMRTP");
				logger.debug("AuthorisationCallback to Esymac is success ");
				success = true;
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("Connection to Esymac is Failed ");
				Thread.sleep(5000);
				maxTries--;
				logger.error("No. of tries left "+maxTries);
			}
		} while (maxTries > 0 && !success);*/
		return success;
	}
}

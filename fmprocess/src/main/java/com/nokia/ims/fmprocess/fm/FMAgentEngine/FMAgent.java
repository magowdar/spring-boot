package com.nokia.ims.fmprocess.fm.FMAgentEngine;

import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmAlarmSyncEngine;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmNativeCodeEngine;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmContext;
import com.nokia.ims.fmprocess.fm.PipelineEngine.PipelineInitiator;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import com.nokia.ims.fmprocess.fm.ems.cmparamlib.CMConfigParamLibrary;
import com.nokia.ims.fmprocess.fm.ems.httpslib.JSONHeartBeat;
import com.nokia.ims.fmprocess.fm.ems.util.Utilities;
import com.nokia.ims.fmprocess.model.AlarmEvent;
import com.nokia.ims.fmprocess.service.AlarmDAOService;
import com.nokia.ims.util.fileutil.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

@Component
public class FMAgent implements CommandLineRunner {

    static Logger logger = LogManager.getLogger(FMAgent.class.getName());

    @Autowired
    private AlarmDAOService alarmDAOService;

    public static String outputType = "ESYMAC";
    private static String neType = "";


    @Override
    public void run(String... args) throws Exception {
        try {
            logger.debug("FMAgent ### Started inside main().");
            FmNativeCodeEngine tspMgr = new FmNativeCodeEngine();
            if (args.length > 0) {
                logger.debug("Reading Property file from the path " + args[0]);
                configureProperties(args[0]);
            } else {
                configureProperties("RtpFMProperty.prop");
            }
           /* getOutputInterface();
            if ("ESYMAC".equalsIgnoreCase(outputType)) {
                prepareEDSRules();
               // prepareAdaptation();
                setSyncLimitFlag();
            }*/
           AlarmEvent alarmEvent = createDummyAlarmEvent();
           alarmDAOService.saveAlarm(alarmEvent);
            setDataBase();
            prepareCorrelationRules();
            logger.debug("Process correlation rule completed");

            logger.info("Initialising PipeLine to Process Events");
            PipelineInitiator.init("ECOMP");
            if ("ESYMAC".equalsIgnoreCase(outputType)) {
                //startHistoryAlarmSync();
            } else {
                FmContext.startNotifyEvent = true;
            }
            startTSPManager(tspMgr);
            logger.debug("Starting receiving TSP messages");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    private AlarmEvent createDummyAlarmEvent() {
        AlarmEvent alarmEvent = new AlarmEvent();
        return alarmEvent;
    }

    public static void startTSPManager(FmNativeCodeEngine tspMgr) {
        Thread tspReader = new Thread(tspMgr, "FmNativeCodeEngine");
        tspReader.start();
    }

    public static void getOutputInterface() {
        try {
            CMConfigParamLibrary cmrelayRef = new CMConfigParamLibrary();
            //outputType = cmrelayRef.getParamValue("REST_FMAgent.EmsProtocol", FmUtil.cmRepoCompName);
            outputType = "REST";
            //neType = Utilities.getParam("NEType");
            neType = "CMREPO";
            if ("REST".equalsIgnoreCase(outputType) && "CMREPO".equalsIgnoreCase(neType)) {
                outputType = "ECOMP";
                FmUtil.HISTORY_SYNC_LIMIT = 10000;
//                String lastEventNum = FileUtil.readContentOfFile(FmUtil.HEARTBEAT_BUFFER_FILE_DIR,FmUtil.HEARTBEAT_BUFFER_FILE);
                String lastEventNum = "1";
                logger.info("Event Number during start up : " + lastEventNum);
                int startingEvtNum = Integer.parseInt(lastEventNum) + 1;
                JSONHeartBeat.setEventNumber(startingEvtNum);
            } else
                outputType = "ESYMAC";
            logger.info("outputType parameter value is " + outputType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /*private static void startHistoryAlarmSync() {
        FmAlarmSyncEngine fmalmsync = new FmAlarmSyncEngine();
        fmalmsync.setName("FmAlarmSyncEngine");
        fmalmsync.start();
    }*/

    public static void configureProperties(String fmPropertyFileName) {
        Properties allProp = new Properties();
        System.out.println("fmPropertyFileName" + fmPropertyFileName );

        File file = new File(ClassLoader.getSystemClassLoader().getResource(fmPropertyFileName).getFile());
        try (FileReader fr = new FileReader(file)) {
            allProp.load(fr);
            FmUtil.setAllProperties(allProp);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void setDataBase() throws Exception {

        FmHsqlDBReady hsql = new FmHsqlDBReady();
        logger.debug("HsqlDB is Initialised.");
        hsql.createAlarmTables();
        logger.debug("HsqlDB tables are created.");
    }

    public static void prepareCorrelationRules() {
        FmRulesEngine ruleEngineRef = new FmRulesEngine();
        logger.debug("Inputs given " + FmUtil.ECR_EXT_PATTERN + " " + FmUtil.DDR_EXT_PATTERN + " " + FmUtil.ECR_PATH_1
                + " " + FmUtil.ECR_PATH_2);
        ruleEngineRef.processCorrelationRules(FmUtil.ECR_EXT_PATTERN, FmUtil.DDR_EXT_PATTERN, FmUtil.ECR_PATH_1,
                FmUtil.ECR_PATH_2);
        logger.debug("Process correlation rule completed");
    }

    /*
     * Eds rules need to be read to determine which all pipe to push a clear alarm
     */
    public static void prepareEDSRules() {
        FmEDSEngine ruleEngineRef = new FmEDSEngine();
        ruleEngineRef.processEDSRules(FmUtil.ECR_PATH_1, FmUtil.ECR_PATH_2);
        logger.info("Processing EDS done");
    }

    /*public static void prepareAdaptation() throws InterruptedException {
        boolean success = false;
        int maxTries = FmUtil.MAX_TRIES;
        do {
            try {
                new FmAdaptationEngine().prepAdaptation();
                success = true;
                logger.info("Adaptation reading Complete");
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("Failed to read Adaptations, Will try Again after 5 seconds");
                Thread.sleep(5000);
                maxTries--;
                logger.error("No. of tries left " + maxTries);
            }
        } while (maxTries > 0 && !success);
    }*/

    public static void setSyncLimitFlag() {
        try {
            //CMConfigParamLibrary cmrelayRef = new CMConfigParamLibrary();
            /*FmUtil.SyncLimitFlag = Boolean
                    .parseBoolean(cmrelayRef.getParamValue("Process_Complete_History.Enabled", FmUtil.cmRepoCompName));*/
            FmUtil.SyncLimitFlag = false;
            logger.info("ProcessCompleteHistory parameter value is " + FmUtil.SyncLimitFlag);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}

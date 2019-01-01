package com.nokia.ims.fmprocess.fm.NativeCodeEngine;

import com.nokia.ims.fmprocess.fm.ems.httpslib.JSONHeartBeat;
import com.nokia.ims.fmprocess.fm.EcompManager.SetupEcomp;
import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FMAgent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmAlarmFilter;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmEDSEngine;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmContext;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningProcessingPipe;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import com.nokia.ims.fmprocess.model.AlarmEvent;
import com.nokia.ims.fmprocess.service.AlarmDAOService;
import com.nokia.ims.util.fileutil.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

@Component
public class FmNativeCodeEngine implements Runnable {

    static Logger logger = LogManager.getLogger(FmNativeCodeEngine.class
            .getName());


    private AlarmDAOService alarmDAOService = new AlarmDAOService();

    /*final static int RTP_EVENT_MSG = 917505;
    final static int ALARM_FILTER_RULES_MSG = 921;
    final static int ECOMP_TABLE_MSG = 922;
    final static int ECOMP_SCALAR_MSG = 923;
    final static int RTP_SHUTDOWNREQUEST_MSG = 65537;

    public static ArrayList<FmAlarmFilter> filterRules = new ArrayList<FmAlarmFilter>();

    private static FmEvent singleTSPEvent = new FmEvent();

    public Set<String> criticalList = FmContext.criticalList;
    public Set<String> majorList = FmContext.majorList;
    public Set<String> minorList = FmContext.minorList;
    public Set<String> warningList = FmContext.warningList;



    public void registerToTSP() {
        if (NativeCode.registerToNodeManagerNative() == 0) {
            logger.info("Registration to TSP is success");
            if (NativeCode.registerForEventsNative(singleTSPEvent) == 0)
                logger.info("Event Registration to TSP is success");
            else
                logger.info("Event Registration to TSP is failed");
        } else {
            logger.error("Registration to TSP failed");
            System.exit(0);
        }
    }

    public void registerToFilters() {
        if (NativeCode.getFilterRulesFromNative(filterRules) == 0) {
            logger.info("Filter Rules Registration to TSP is success");
            setFilterRules();
        } else
            logger.info("Filter Rules Registration to TSP is failed");
    }

    private void setFilterRules() {
        logger.info("Initialising Alarm-Filter Rules");
        FmAlarmFilter.initAlarmFilterList(filterRules);
        filterRules.clear();
        logger.debug("Cleared memory of FilterRules object and size is now "
                + filterRules.size());
    }

    public void registerToThreshold() {
        NativeCode.getThresholdFromNative();
    }



    public void recoverTSPEventRange(ArrayList<FmEvent> eventRecoverList,
                                     long startSeqId, long endSeqId) {
        NativeCode.recoverTSPEventRangeNative(eventRecoverList, startSeqId,
                endSeqId);
    }

    public void recoverTSPEventList(ArrayList<FmEvent> esymacAlarmList,
                                    long[] seqList) {
        NativeCode.recoverTSPEventListNative(esymacAlarmList, seqList);
    }

    public void recoverTSPEventBulk(ArrayList<FmEvent> eventRecoverList,
                                    long latestTspSeq, long lastSeqProcessed, int maxEvents) {
        NativeCode.recoverTSPEventBulkNative(eventRecoverList, latestTspSeq,
                lastSeqProcessed, maxEvents);
    }

    public long latestTSPSeqId() {
        return NativeCode.latestTSPSeqIdNative();
    }

    public static boolean authorizeUser(String userName, String operation,
                                        String service) {
        return NativeCode.authorizeUserNative(userName, operation, service);
    }
*/


    private void setHeartBeatInterval(Integer hbInterval) {
        logger.info("Initialising HeartBeatInterval");
        SetupEcomp.setHeartBeatInterval(hbInterval);
    }

    private void setEcompScalar() {
        logger.info("Initialising Scalar Ecomp Params");
        SetupEcomp.setEcompScalar();
    }

    private void setEcompTable() {
        logger.info("Initialising Table Ecomp Params");
        SetupEcomp.setEcompTable();
    }

    @Override
    public void run() {
        setHeartBeatInterval(60);
        setEcompScalar();
        //setEcompTable();
        AlarmEvent currentEvent = new AlarmEvent();
        EcompProcessingPipe ecompPipe = FmContext.getEcompPipeRef();
        BlockingQueue<AlarmEvent> alarms = alarmDAOService.getAlarms();
        while (true) {
            System.out.println("Inside While loop");
            try {
                currentEvent = alarms.take();
               // ecompPipe.write(currentEvent);
            } catch (InterruptedException e) {
                logger.info("Error while writing alarms to Ecomp");
            }
        }

        /* int msgType;
         *//*Starting different pipes minor major critical and warning for alarm processing *//*
        MinorProcessingPipe minorPipe = FmContext.getMinorPipeRef();
        MajorProcessingPipe majorPipe = FmContext.getMajorPipeRef();
        WarningProcessingPipe warningPipe = FmContext.getWarningPipeRef();
        CriticalProcessingPipe criticalPipe = FmContext.getCriticalPipeRef();
        EcompProcessingPipe ecompPipe = FmContext.getEcompPipeRef();
        while (true) {
            msgType = NativeCode.receiveSignalAndEventsFromTSPNative();
            switch (msgType) {
                case RTP_EVENT_MSG:
                    if (FmContext.startNotifyEvent
                            && singleTSPEvent.SEQUENCE_ID > FmContext.lastSeqSynced) {


                        FmEvent currentEvent = new FmEvent(singleTSPEvent);
                        logger.info("Pushing the Alarm to PipeLine EntryPoint "
                                + currentEvent.SEQUENCE_ID + " " + currentEvent.EVENT_ID);
                        try {
                            if ("ESYMAC".equalsIgnoreCase(FMAgent.outputType)) {

                                if (currentEvent.EVENT_SEVERITY == 1) {
                                    criticalPipe.write(currentEvent);
                                } else if (currentEvent.EVENT_SEVERITY == 2) {
                                    majorPipe.write(currentEvent);
                                } else if (currentEvent.EVENT_SEVERITY == 3) {
                                    minorPipe.write(currentEvent);
                                } else if (currentEvent.EVENT_SEVERITY == 4) {
                                    warningPipe.write(currentEvent);
                                } else if (currentEvent.EVENT_SEVERITY == 6) {

                                    if ((FmEDSEngine.clearCriticalEventList.get(currentEvent.EVENT_ID)) != null) {
                                        logger.info("Pushing the ClearAlarm" + currentEvent.EVENT_ID + " to Critical Pipe " + currentEvent.SEQUENCE_ID);
                                        criticalPipe.write(currentEvent);
                                    }
                                    if ((FmEDSEngine.clearMajorEventList.get(currentEvent.EVENT_ID)) != null) {
                                        logger.info("Pushing the ClearAlarm" + currentEvent.EVENT_ID + " to Major Pipe " + currentEvent.SEQUENCE_ID);
                                        majorPipe.write(currentEvent);
                                    }
                                    if ((FmEDSEngine.clearMinorEventList.get(currentEvent.EVENT_ID)) != null) {
                                        logger.info("Pushing the ClearAlarm" + currentEvent.EVENT_ID + " to Minor Pipe " + currentEvent.SEQUENCE_ID);
                                        minorPipe.write(currentEvent);
                                    }
                                    if ((FmEDSEngine.clearWarningEventList.get(currentEvent.EVENT_ID)) != null) {
                                        logger.info("Pushing the ClearAlarm" + currentEvent.EVENT_ID + " to Warning Pipe " + currentEvent.SEQUENCE_ID);
                                        warningPipe.write(currentEvent);
                                    }
                                }
                            } else {
                                ecompPipe.write(currentEvent);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            logger.error("Coud not write Alarm to PipeLine");
                            e.printStackTrace();
                        }
                    }
                    break;
                case ALARM_FILTER_RULES_MSG:
                    if ("ESYMAC".equalsIgnoreCase(FMAgent.outputType))
                        setFilterRules();
                    break;
                case ECOMP_TABLE_MSG:
                    if ("ECOMP".equalsIgnoreCase(FMAgent.outputType))
                        setEcompTable();
                    break;
                case ECOMP_SCALAR_MSG:
                    if ("ECOMP".equalsIgnoreCase(FMAgent.outputType))
                        setEcompScalar();
                    break;
                default:
                    if ("ECOMP".equalsIgnoreCase(FMAgent.outputType))
                        setHeartBeatInterval(msgType);
                    break;
                case RTP_SHUTDOWNREQUEST_MSG:
                    logger.info("Updating event Number in filr");
                    FileUtil.truncateAndWriteIntegerDataToFile(FmUtil.HEARTBEAT_BUFFER_FILE_DIR, FmUtil.HEARTBEAT_BUFFER_FILE, JSONHeartBeat.getEventNumber());
                    logger.info("Received Shutdown signal. So Exiting.");
                    System.exit(0);
            }
        }*/
    }


}

package com.nokia.ims.fmprocess.service;

import com.nokia.ims.fmprocess.model.AlarmEvent;

import com.nokia.ims.fmprocess.model.ves.v53.model.common.Field;
import com.nokia.ims.fmprocess.model.ves.v53.model.commonEventHeader.CommonEventHeader;
import com.nokia.ims.fmprocess.model.ves.v53.model.fault.FaultEvent;
import com.nokia.ims.fmprocess.model.ves.v53.model.fault.FaultFields;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EcompAlarmProcessingService {

     public FaultEvent processAlarm(AlarmEvent alarm){
         return convertToEcompAlarm(alarm);

     }

    private FaultEvent convertToEcompAlarm(AlarmEvent alarm) {
        FaultEvent ecompAlarm  = new FaultEvent();
        CommonEventHeader commonEventHeader = setCommonEventHeaderFields(alarm);
        ecompAlarm.setCommonEventHeader(commonEventHeader);
        FaultFields faultFields = setFaultFields(alarm);
        ecompAlarm.setFaultFields(faultFields);
        return ecompAlarm;
    }

    private FaultFields setFaultFields(AlarmEvent alarm) {
         FaultFields faultFields = new FaultFields();
         faultFields.setSpecificProblem(String.valueOf(alarm.getSpecificProblem()));
         faultFields.setAlarmAdditionalInformation(new ArrayList<Field>());
         faultFields.setAlarmCondition(alarm.getEventFaultyObject());
         faultFields.setAlarmInterfaceA(alarm.getAlarmName());
         faultFields.setEventCategory(String.valueOf(alarm.getEventType()));
         faultFields.setEventSeverity(String.valueOf(alarm.getEventSeverity()));
         faultFields.setFaultFieldsVersion(2.0);
         faultFields.setVfStatus("Active");
         return faultFields;
    }

    private CommonEventHeader setCommonEventHeaderFields(AlarmEvent alarm) {
        CommonEventHeader commonEventHeader = new CommonEventHeader();
        commonEventHeader.setDomain("Fault");
        commonEventHeader.setEventId("001-00000000000001");
        commonEventHeader.setEventName("CSCF_Nokia" + alarm.getEventId());
        commonEventHeader.setEventType(String.valueOf(alarm.getEventType()));
        commonEventHeader.setLastEpochMicrosec(System.currentTimeMillis());
        commonEventHeader.setNfcNamingCode(alarm.getNfcCode());
        commonEventHeader.setNfNamingCode(alarm.getVnfName());
        commonEventHeader.setPriority("High");
        commonEventHeader.setReportingEntityId("oam-Id");
        commonEventHeader.setReportingEntityName("oam");
        commonEventHeader.setSequence(1);
        commonEventHeader.setStartEpochMicrosec(alarm.getEventTime());
        commonEventHeader.setSourceId("sourceId");
        commonEventHeader.setVersion(3.0);
        commonEventHeader.setSourceName(alarm.getEventProcessName());
        return commonEventHeader;
    }


}

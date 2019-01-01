package com.nokia.ims.fmprocess.model.ves.v53;

import com.nokia.ims.fmprocess.model.ves.v53.model.common.Field;
import com.nokia.ims.fmprocess.model.ves.v53.model.commonEventHeader.CommonEventHeader;
import com.nokia.ims.fmprocess.model.ves.v53.model.fault.FaultEvent;
import com.nokia.ims.fmprocess.model.ves.v53.model.fault.FaultFields;
import com.nokia.ims.fmprocess.model.ves.v53.util.JacksonUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SampleEventsCreator {

    public static void main(String [] args){
        FaultEvent event = createEvent();
        System.out.println("JSON is : " + JacksonUtil.convertFaultEventToJson(event));
    }


    private static FaultEvent createEvent() {
        FaultEvent event = new FaultEvent();
        CommonEventHeader commonEventHeader = createCommonEventHeader();
        FaultFields faultFields = createFaultFields();
        event.setCommonEventHeader(commonEventHeader);
        event.setFaultFields(faultFields);
        return event;
    }

    private static FaultFields createFaultFields() {
        FaultFields faultFields = new FaultFields();
        faultFields.setAlarmCondition("alarmCondition");
        faultFields.setAlarmInterfaceA("alarmInterfaceA");
        faultFields.setEventCategory("eventCatagory");
        faultFields.setEventSeverity("MAJOR");
        faultFields.setEventSourceType("sourceType");
        faultFields.setFaultFieldsVersion(2.0);
        faultFields.setSpecificProblem("specificProblem");
        faultFields.setVfStatus("Active");
        List<Field> alarmAdditionalInformation = createAlarmAdditionalInformation();

        faultFields.setAlarmAdditionalInformation(alarmAdditionalInformation);

        return faultFields;
    }

    private static List<Field> createAlarmAdditionalInformation() {
        List<Field> alarmAdditionalInformation = new ArrayList<Field>();
        Field field = new Field();
        field.setName("AlarmType");
        field.setValue("value");
        alarmAdditionalInformation.add(field);
        return alarmAdditionalInformation;
    }

    private static CommonEventHeader createCommonEventHeader() {
        CommonEventHeader commonEventHeader = new CommonEventHeader();
        commonEventHeader.setDomain("fault");
        commonEventHeader.setEventId("001-000000000001");
        commonEventHeader.setEventName("Fault_vCSCFicsf_DBErrror-170-342");
        commonEventHeader.setVersion(3.0);
        commonEventHeader.setEventType("alarmType");
        commonEventHeader.setStartEpochMicrosec(Instant.now().toEpochMilli());
        commonEventHeader.setNfcNamingCode("CDI");
        commonEventHeader.setPriority("High");
        commonEventHeader.setNfNamingCode("CSCF");
        commonEventHeader.setReportingEntityId("reporting_entity_id_123abc");
        commonEventHeader.setReportingEntityName("reporting_entity_name_oam");
        commonEventHeader.setSequence(0);
        commonEventHeader.setSourceId("source_entity_id_123abc");
        commonEventHeader.setSourceName("source_entity_name_oam");
        commonEventHeader.setLastEpochMicrosec(Instant.now().toEpochMilli());
        return commonEventHeader;
    }

}

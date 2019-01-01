package com.manju.fmprocess.jpa.alarmsjpa.util;

import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;

import java.util.Date;

public class AlarmUtil {

    public static AlarmEvent createAlarmEvent() {
        AlarmEvent event = new AlarmEvent();
        event.setEventCEName("CE_Name");
        event.setEventClearUser("NE_User");
        event.setEventFaultyObject("HPProbook");
        event.setEventId("103-120");
        event.setEventParameter("s=FAN^0");
        event.setEventProcessName("AIBots");
        event.setEventShortText("SubSystem Not Available");
        event.setEventTime(new Date().getTime());
        event.setEventSeverity(2);
        event.setEventType(3);
        event.setSpecificProblem(6750328);
        return event;
    }

}

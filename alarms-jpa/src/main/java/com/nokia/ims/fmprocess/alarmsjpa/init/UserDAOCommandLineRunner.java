package com.nokia.ims.fmprocess.alarmsjpa.init;

import com.nokia.ims.fmprocess.alarmsjpa.model.AlarmEvent;
import com.nokia.ims.fmprocess.alarmsjpa.service.AlarmDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserDAOCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOCommandLineRunner.class);
    @Autowired
    private AlarmDAOService daoService;

    @Override
    public void run(String... args) throws Exception {
        AlarmEvent event = createAlarmEvent();
        daoService.saveAlarm(event);
        logger.info("ALarms inserted : " + event.getId());

    }

    private AlarmEvent createAlarmEvent() {
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

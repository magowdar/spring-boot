package com.manju.fmprocess.jpa.alarmsjpa.init;

import com.manju.fmprocess.jpa.alarmsjpa.service.AlarmDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AlarmsDAOCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AlarmsDAOCommandLineRunner.class);
    @Autowired
    private AlarmDAOService daoService;

    @Override
    public void run(String... args) throws Exception {
        /*AlarmEvent event = createAlarmEvent();
        daoService.saveAlarm(event);
        logger.info("ALarms inserted : " + event.getId());*/

    }

}

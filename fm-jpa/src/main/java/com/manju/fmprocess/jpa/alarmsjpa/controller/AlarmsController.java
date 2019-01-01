package com.manju.fmprocess.jpa.alarmsjpa.controller;

import com.manju.fmprocess.jpa.alarmsjpa.client.FmProcessServiceProxy;
import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import com.manju.fmprocess.jpa.alarmsjpa.service.AlarmProcessingService;
import com.manju.fmprocess.jpa.alarmsjpa.service.AlarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlarmsController {

    @Autowired
    private AlarmRepository alarmDAOService;

    @Autowired
    private FmProcessServiceProxy proxy;

    @Autowired
    private AlarmProcessingService alarmProcessingService;

    private static Logger logger = LoggerFactory.getLogger(AlarmsController.class);

    @GetMapping("/getAlarmById/{id}")
    public Optional<AlarmEvent> getAlarmById(@PathVariable long id){
        return alarmDAOService.findById(id);
    }

    @PostMapping("/saveAlarm")
    public AlarmEvent saveAlarm(@RequestBody AlarmEvent alarm){
        return alarmDAOService.save(alarm);
    }

    @GetMapping("/getAlarms")
    public List<AlarmEvent> getAlarms(){
        return alarmDAOService.findAll();
    }

    @PostMapping("/alarms-jpa/processAlarm")
    public void processAlarm(@RequestBody AlarmEvent alarm){
          alarmDAOService.save(alarm);
        String response = proxy.postAlarm(alarm);
        logger.info("Response from Alarm processing service " + response);
    }
}

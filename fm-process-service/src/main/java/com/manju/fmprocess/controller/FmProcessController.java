package com.manju.fmprocess.controller;


import com.manju.fmprocess.model.AlarmEvent;
import com.manju.fmprocess.model.ves.v53.model.fault.FaultEvent;
import com.manju.fmprocess.service.EcompAlarmProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FmProcessController {

    @Autowired
    private EcompAlarmProcessingService alarmProcessingService;

    @PostMapping("/fmprocess/processAlarm-to-ecomp")
    public String processAlarm(@RequestBody AlarmEvent alarm){
        FaultEvent faultEvent = alarmProcessingService.processAlarm(alarm);
        return "Alarm :" + alarm.getEventId() + " Sent to Ecomp Successfully";
    }
}

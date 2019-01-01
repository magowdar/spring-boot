package com.nokia.ims.fmprocess.controller;

import com.nokia.ims.fmprocess.model.AlarmEvent;
import com.nokia.ims.fmprocess.service.AlarmDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.BlockingQueue;

@RestController
public class AlarmsController {

    @Autowired
    private AlarmDAOService alarmDAOService;

    @RequestMapping(method = RequestMethod.GET,path="/hello-world")
    public String helloWorld(){
        return "HelloWorld";
    }

    @RequestMapping(method = RequestMethod.GET,path = "/alarms")
    public BlockingQueue<AlarmEvent> getAlarms(){
            return alarmDAOService.getAlarms();
    }

    @RequestMapping(method = RequestMethod.GET,path = "/alarm")
    public AlarmEvent getAlarm(){
        return alarmDAOService.retriveAlarmFromStore();
    }

    @RequestMapping(method = RequestMethod.POST,path = "/alarms")
    public void storeAlarm(@RequestBody AlarmEvent alarm){
        alarmDAOService.saveAlarm(alarm);
    }

}

package com.manju.fmprocess.jpa.alarmsjpa.service;

import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.stereotype.Component;

@Component
public class AlarmProcessingService {

     public AlarmEvent processAlarm(AlarmEvent alarm){
            return new AlarmEvent();
    }
}

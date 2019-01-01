package com.nokia.ims.fmprocess.service;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.model.AlarmEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@ComponentScan
public class AlarmDAOService {

    private static int MAX_NUMBER_OF_ALARMS = 10000;
    private static BlockingQueue<AlarmEvent> alarms = new ArrayBlockingQueue<AlarmEvent>(MAX_NUMBER_OF_ALARMS);
    static {
        AlarmEvent alarm = new AlarmEvent();
        alarm.setEcompEventId("001-FM-0000001");
        alarm.setEcompEventTime(new Date().getTime());
        alarm.setEcompSequenceId(1);
        alarm.setEventCEName("evt_ce_name");
        alarm.setEventClearUser("NE_USER");
        alarm.setEventFaultyObject("FaultyObj");
        alarm.setEventId("evtId");
        alarm.setEventParameter("parameters");
        alarm.setEventProcessName("process_Name");
        alarm.setEventSeverity(2);
        alarm.setEventShortText("short");
        alarm.setEventTime(new Date().getTime());
        alarm.setEventType(2);
        alarm.setSpecificProblem(1233333);
        alarm.setSequenceId(2);


        alarms.add(alarm);
    }

    public AlarmEvent saveAlarm(AlarmEvent alarm){
        alarms.offer(alarm);
        return alarm;
    }

    public AlarmEvent retriveAlarmFromStore(){
        AlarmEvent alarmEvent = null;
        try {
            alarmEvent = alarms.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return alarmEvent;
    }

    public List<AlarmEvent> findByEventId(String evtId){
        List<AlarmEvent> foundAlarms = new ArrayList<AlarmEvent>();
        for(AlarmEvent alarmEvent : alarms){
            if(alarmEvent.getEventId().equals(evtId)){
                foundAlarms.add(alarmEvent);
            }
        }
        return foundAlarms;
    }

    public BlockingQueue<AlarmEvent> getAlarms(){
            return alarms;
    }

}

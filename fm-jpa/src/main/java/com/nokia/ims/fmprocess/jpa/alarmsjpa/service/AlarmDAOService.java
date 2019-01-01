package com.nokia.ims.fmprocess.jpa.alarmsjpa.service;

import com.nokia.ims.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AlarmDAOService {

    @PersistenceContext
    private EntityManager entityManager;

    public long saveAlarm(AlarmEvent alarm){
        entityManager.persist(alarm);
        return alarm.getId();
    }

    public AlarmEvent findAlarm(long id){
        return entityManager.find(AlarmEvent.class,id);
    }

    /*public List<AlarmEvent> getAlarms(){
        return entityManager.
    }*/
 }

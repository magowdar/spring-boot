package com.manju.fmprocess.jpa.alarmsjpa.service;

import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

package com.manju.fmprocess.alarmsjpa.service;

import com.manju.fmprocess.alarmsjpa.model.AlarmEvent;
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
}

package com.manju.fmprocess.jpa.alarmsjpa.service;

import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmEvent, Long> {
}

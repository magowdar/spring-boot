package com.nokia.ims.fmprocess.jpa.alarmsjpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public @Data
@NoArgsConstructor
class AlarmEvent {

    @Id
    @GeneratedValue
    private long id;

    private long sequenceId;
    private int eventType;
    private int eventSeverity;
    private int specificProblem;
    private long eventTime;
    private String eventId;
    private String eventCEName;
    private String eventProcessName;
    private String eventShortText;
    private String eventParameter;
    private String eventFaultyObject;
    private String eventClearUser;
    private String nfcCode;
    private String alarmName;
    private String alarmGroupName;
    private String vnfName;

}

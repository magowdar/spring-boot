package com.nokia.ims.fmprocess.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class EcompHistoryEvents {
    @Id
    private BigInteger sequenceId;
    private int eventType;
    private int eventSeverity;
    private int specificProblem;
    private BigInteger eventTime;
    private BigInteger eventLastTime;
    private String eventId;
    private String eventCeName;
    private String eventProcName;
    private String eventShortText;
    private String eventParameter;
    private String eventFaultyObject;
    private String ecompEventId;
    private String ecompSequenceId;

    public EcompHistoryEvents() {
    }

    public EcompHistoryEvents(BigInteger sequenceId, int eventType, int eventSeverity, int specificProblem, BigInteger eventTime, BigInteger eventLastTime, String eventId, String eventCeName, String eventProcName, String eventShortText, String eventParameter, String eventFaultyObject, String ecompEventId, String ecompSequenceId) {
        this.sequenceId = sequenceId;
        this.eventType = eventType;
        this.eventSeverity = eventSeverity;
        this.specificProblem = specificProblem;
        this.eventTime = eventTime;
        this.eventLastTime = eventLastTime;
        this.eventId = eventId;
        this.eventCeName = eventCeName;
        this.eventProcName = eventProcName;
        this.eventShortText = eventShortText;
        this.eventParameter = eventParameter;
        this.eventFaultyObject = eventFaultyObject;
        this.ecompEventId = ecompEventId;
        this.ecompSequenceId = ecompSequenceId;
    }

    public BigInteger getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(BigInteger sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventSeverity() {
        return eventSeverity;
    }

    public void setEventSeverity(int eventSeverity) {
        this.eventSeverity = eventSeverity;
    }

    public int getSpecificProblem() {
        return specificProblem;
    }

    public void setSpecificProblem(int specificProblem) {
        this.specificProblem = specificProblem;
    }

    public BigInteger getEventTime() {
        return eventTime;
    }

    public void setEventTime(BigInteger eventTime) {
        this.eventTime = eventTime;
    }

    public BigInteger getEventLastTime() {
        return eventLastTime;
    }

    public void setEventLastTime(BigInteger eventLastTime) {
        this.eventLastTime = eventLastTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventCeName() {
        return eventCeName;
    }

    public void setEventCeName(String eventCeName) {
        this.eventCeName = eventCeName;
    }

    public String getEventProcName() {
        return eventProcName;
    }

    public void setEventProcName(String eventProcName) {
        this.eventProcName = eventProcName;
    }

    public String getEventShortText() {
        return eventShortText;
    }

    public void setEventShortText(String eventShortText) {
        this.eventShortText = eventShortText;
    }

    public String getEventParameter() {
        return eventParameter;
    }

    public void setEventParameter(String eventParameter) {
        this.eventParameter = eventParameter;
    }

    public String getEventFaultyObject() {
        return eventFaultyObject;
    }

    public void setEventFaultyObject(String eventFaultyObject) {
        this.eventFaultyObject = eventFaultyObject;
    }

    public String getEcompEventId() {
        return ecompEventId;
    }

    public void setEcompEventId(String ecompEventId) {
        this.ecompEventId = ecompEventId;
    }

    public String getEcompSequenceId() {
        return ecompSequenceId;
    }

    public void setEcompSequenceId(String ecompSequenceId) {
        this.ecompSequenceId = ecompSequenceId;
    }

    @Override
    public String toString() {
        return "EcompHistoryEvents{" +
                "sequenceId=" + sequenceId +
                ", eventType=" + eventType +
                ", eventSeverity=" + eventSeverity +
                ", specificProblem=" + specificProblem +
                ", eventTime=" + eventTime +
                ", eventLastTime=" + eventLastTime +
                ", eventId='" + eventId + '\'' +
                ", eventCeName='" + eventCeName + '\'' +
                ", eventProcName='" + eventProcName + '\'' +
                ", eventShortText='" + eventShortText + '\'' +
                ", eventParameter='" + eventParameter + '\'' +
                ", eventFaultyObject='" + eventFaultyObject + '\'' +
                ", ecompEventId='" + ecompEventId + '\'' +
                ", ecompSequenceId='" + ecompSequenceId + '\'' +
                '}';
    }
}


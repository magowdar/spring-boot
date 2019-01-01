package com.manju.fmprocess.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class HistoryEvents {

    @Id
    private BigInteger sequenceId;
    private int eventType;
    private int eventSeverity;
    private int specificProblem;
    private BigInteger eventTime;
    private String eventId;
    private String eventCeName;
    private String eventProcName;
    private String eventShortText;
    private String eventParameter;
    private String eventFaultyObject;

    public HistoryEvents() {
    }

    public HistoryEvents(BigInteger sequenceId, int eventType, int eventSeverity, int specificProblem, BigInteger eventTime, String eventId, String eventCeName, String eventProcName, String eventShortText, String eventParameter, String eventFaultyObject) {
        this.sequenceId = sequenceId;
        this.eventType = eventType;
        this.eventSeverity = eventSeverity;
        this.specificProblem = specificProblem;
        this.eventTime = eventTime;
        this.eventId = eventId;
        this.eventCeName = eventCeName;
        this.eventProcName = eventProcName;
        this.eventShortText = eventShortText;
        this.eventParameter = eventParameter;
        this.eventFaultyObject = eventFaultyObject;
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


    @Override
    public String toString() {
        return "HistoryEvents{" +
                "sequenceId=" + sequenceId +
                ", eventType=" + eventType +
                ", eventSeverity=" + eventSeverity +
                ", specificProblem=" + specificProblem +
                ", eventTime=" + eventTime +
                ", eventId='" + eventId + '\'' +
                ", eventCeName='" + eventCeName + '\'' +
                ", eventProcName='" + eventProcName + '\'' +
                ", eventShortText='" + eventShortText + '\'' +
                ", eventParameter='" + eventParameter + '\'' +
                ", eventFaultyObject='" + eventFaultyObject + '\'' +
                '}';
    }
}


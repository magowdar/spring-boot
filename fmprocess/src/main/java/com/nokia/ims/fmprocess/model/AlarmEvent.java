package com.nokia.ims.fmprocess.model;

public class AlarmEvent {

    public long sequenceId;
    public int ecompSequenceId;
    public int eventType;
    public int eventSeverity;
    public int specificProblem;
    public long eventTime;
    public long ecompEventTime;

    public String eventId;
    public String ecompEventId;
    public String eventCEName;
    public String eventProcessName;
    public String eventShortText;
    public String eventParameter;
    public String eventFaultyObject;
    public String eventClearUser;

    public AlarmEvent() {
    }

   /* public AlarmEvent(long sequenceId, int eventType, int eventSeverity, int specificProblem, long eventTime,String eventId, String eventCEName, String eventProcessName, String eventShortText, String eventParameter, String eventFaultyObject, String eventClearUser) {
        this.sequenceId = sequenceId;
        this.eventType = eventType;
        this.eventSeverity = eventSeverity;
        this.specificProblem = specificProblem;
        this.eventTime = eventTime;
        this.eventId = eventId;
        this.eventCEName = eventCEName;
        this.eventProcessName = eventProcessName;
        this.eventShortText = eventShortText;
        this.eventParameter = eventParameter;
        this.eventFaultyObject = eventFaultyObject;
        this.eventClearUser = eventClearUser;
    }*/

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getEcompSequenceId() {
        return ecompSequenceId;
    }

    public void setEcompSequenceId(int ecompSequenceId) {
        this.ecompSequenceId = ecompSequenceId;
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

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public long getEcompEventTime() {
        return ecompEventTime;
    }

    public void setEcompEventTime(long ecompEventTime) {
        this.ecompEventTime = ecompEventTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEcompEventId() {
        return ecompEventId;
    }

    public void setEcompEventId(String ecompEventId) {
        this.ecompEventId = ecompEventId;
    }

    public String getEventCEName() {
        return eventCEName;
    }

    public void setEventCEName(String eventCEName) {
        this.eventCEName = eventCEName;
    }

    public String getEventProcessName() {
        return eventProcessName;
    }

    public void setEventProcessName(String eventProcessName) {
        this.eventProcessName = eventProcessName;
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

    public String getEventClearUser() {
        return eventClearUser;
    }

    public void setEventClearUser(String eventClearUser) {
        this.eventClearUser = eventClearUser;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "sequenceId=" + sequenceId +
                ", ecompSequenceId=" + ecompSequenceId +
                ", eventType=" + eventType +
                ", eventSeverity=" + eventSeverity +
                ", specificProblem=" + specificProblem +
                ", eventTime=" + eventTime +
                ", ecompEventTime=" + ecompEventTime +
                ", eventId='" + eventId + '\'' +
                ", ecompEventId=" + ecompEventId +
                ", eventCEName='" + eventCEName + '\'' +
                ", eventProcessName='" + eventProcessName + '\'' +
                ", eventShortText='" + eventShortText + '\'' +
                ", eventParameter='" + eventParameter + '\'' +
                ", eventFaultyObject='" + eventFaultyObject + '\'' +
                ", eventClearUser='" + eventClearUser + '\'' +
                '}';
    }
}

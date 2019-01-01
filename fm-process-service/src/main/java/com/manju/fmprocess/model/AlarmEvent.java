package com.manju.fmprocess.model;



public class AlarmEvent {

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


    public AlarmEvent() {
    }

    public AlarmEvent(long sequenceId, int eventType, int eventSeverity, int specificProblem, long eventTime, String eventId, String eventCEName, String eventProcessName, String eventShortText, String eventParameter, String eventFaultyObject, String eventClearUser, String nfcCode, String alarmName, String alarmGroupName) {
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
        this.nfcCode = nfcCode;
        this.alarmName = alarmName;
        this.alarmGroupName = alarmGroupName;
    }

    public String getVnfName() {
        return vnfName;
    }

    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
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

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public String getNfcCode() {
        return nfcCode;
    }

    public void setNfcCode(String nfcCode) {
        this.nfcCode = nfcCode;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmGroupName() {
        return alarmGroupName;
    }

    public void setAlarmGroupName(String alarmGroupName) {
        this.alarmGroupName = alarmGroupName;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "id=" + id +
                ", sequenceId=" + sequenceId +
                ", eventType=" + eventType +
                ", eventSeverity=" + eventSeverity +
                ", specificProblem=" + specificProblem +
                ", eventTime=" + eventTime +
                ", eventId='" + eventId + '\'' +
                ", eventCEName='" + eventCEName + '\'' +
                ", eventProcessName='" + eventProcessName + '\'' +
                ", eventShortText='" + eventShortText + '\'' +
                ", eventParameter='" + eventParameter + '\'' +
                ", eventFaultyObject='" + eventFaultyObject + '\'' +
                ", eventClearUser='" + eventClearUser + '\'' +
                ", nfcCode='" + nfcCode + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", alarmGroupName='" + alarmGroupName + '\'' +
                ", vnfName='" + vnfName + '\'' +
                '}';
    }
}

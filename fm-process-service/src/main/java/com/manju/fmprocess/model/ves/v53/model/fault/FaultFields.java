package com.manju.fmprocess.model.ves.v53.model.fault;

import com.manju.fmprocess.model.ves.v53.model.common.Field;

import java.util.List;

public class FaultFields {
    private double faultFieldsVersion;
    private String eventSeverity;
    private String eventSourceType;
    private String eventCategory;
    private String alarmCondition;
    private String specificProblem;
    private String vfStatus;
    private String alarmInterfaceA;
    private List<Field> alarmAdditionalInformation;

    public FaultFields() {
    }

    public double getFaultFieldsVersion() {
        return faultFieldsVersion;
    }

    public void setFaultFieldsVersion(double faultFieldsVersion) {
        this.faultFieldsVersion = faultFieldsVersion;
    }

    public String getEventSeverity() {
        return eventSeverity;
    }

    public void setEventSeverity(String eventSeverity) {
        this.eventSeverity = eventSeverity;
    }

    public String getEventSourceType() {
        return eventSourceType;
    }

    public void setEventSourceType(String eventSourceType) {
        this.eventSourceType = eventSourceType;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getAlarmCondition() {
        return alarmCondition;
    }

    public void setAlarmCondition(String alarmCondition) {
        this.alarmCondition = alarmCondition;
    }

    public String getSpecificProblem() {
        return specificProblem;
    }

    public void setSpecificProblem(String specificProblem) {
        this.specificProblem = specificProblem;
    }

    public String getVfStatus() {
        return vfStatus;
    }

    public void setVfStatus(String vfStatus) {
        this.vfStatus = vfStatus;
    }

    public String getAlarmInterfaceA() {
        return alarmInterfaceA;
    }

    public void setAlarmInterfaceA(String alarmInterfaceA) {
        this.alarmInterfaceA = alarmInterfaceA;
    }

    public List<Field> getAlarmAdditionalInformation() {
        return alarmAdditionalInformation;
    }

    public void setAlarmAdditionalInformation(List<Field> alarmAdditionalInformation) {
        this.alarmAdditionalInformation = alarmAdditionalInformation;
    }

    @Override
    public String toString() {
        return "FaultFields{" +
                "faultFieldsVersion=" + faultFieldsVersion +
                ", eventSeverity='" + eventSeverity + '\'' +
                ", eventSourceType='" + eventSourceType + '\'' +
                ", eventCategory='" + eventCategory + '\'' +
                ", alarmCondition='" + alarmCondition + '\'' +
                ", specificProblem='" + specificProblem + '\'' +
                ", vfStatus='" + vfStatus + '\'' +
                ", alarmInterfaceA='" + alarmInterfaceA + '\'' +
                ", alarmAdditionalInformation=" + alarmAdditionalInformation +
                '}';
    }
}

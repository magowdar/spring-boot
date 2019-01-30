package com.manju.ecomp.model.ves.v5.commonEventHeader;

import javax.validation.Valid;
import javax.validation.constraints.*;


public class CommonEventHeader {
    @Digits(integer = 1,fraction = 1)
    @DecimalMin(value = "3.0")
    @DecimalMax(value="3.0")
    private double version;
    private String eventName;
    @Pattern(regexp = "fault|measurementsForVfScalaing|heartbeat", message = "Domain value should be fault or measurementsForVfScalaing or heartbeat")
    private String domain;
    private String eventId;
    private String eventType;
    private String nfcNamingCode;
    private String nfNamingCode;
    private String sourceId;
    private String sourceName;
    private String reportingEntityId;
    private String reportingEntityName;
    private String priority;
    private long startEpochMicrosec;
    private long lastEpochMicrosec;
    private int sequence;

    public CommonEventHeader() {
    }

    @NotNull(message = "Version shouldnt be blank")

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }


    @NotNull(message = "eventName shouldnt be blank")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @NotNull(message = "eventId shouldnt be blank")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @NotNull(message = "eventType shouldnt be blank")
    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @NotNull(message = "nfcNamingCode shouldnt be blank")
    public String getNfcNamingCode() {
        return nfcNamingCode;
    }

    public void setNfcNamingCode(String nfcNamingCode) {
        this.nfcNamingCode = nfcNamingCode;
    }

    @NotNull(message = "nfNamingCode shouldnt be blank")
    public String getNfNamingCode() {
        return nfNamingCode;
    }


    public void setNfNamingCode(String nfNamingCode) {
        this.nfNamingCode = nfNamingCode;
    }

    @NotNull(message = "sourceId shouldnt be blank")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @NotNull(message = "sourceName shouldnt be blank")
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @NotNull(message = "reportingEntityId shouldnt be blank")
    public String getReportingEntityId() {
        return reportingEntityId;
    }

    public void setReportingEntityId(String reportingEntityId) {
        this.reportingEntityId = reportingEntityId;
    }

    @NotNull(message = "reportingEntityName shouldnt be blank")
    public String getReportingEntityName() {
        return reportingEntityName;
    }

    public void setReportingEntityName(String reportingEntityName) {
        this.reportingEntityName = reportingEntityName;
    }

    @NotNull(message = "priority shouldnt be blank")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @NotNull(message = "startEpochMicrosec shouldnt be blank")
    public long getStartEpochMicrosec() {
        return startEpochMicrosec;
    }

    public void setStartEpochMicrosec(long startEpochMicrosec) {
        this.startEpochMicrosec = startEpochMicrosec;
    }

    @NotNull(message = "lastEpochMicrosec shouldnt be blank")
    public long getLastEpochMicrosec() {
        return lastEpochMicrosec;
    }

    public void setLastEpochMicrosec(long lastEpochMicrosec) {
        this.lastEpochMicrosec = lastEpochMicrosec;
    }

    @NotNull(message = "sequence shouldnt be blank")
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "CommonEventHeader{" +
                "version=" + version +
                ", eventName='" + eventName + '\'' +
                ", domain='" + domain + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", nfcNamingCode='" + nfcNamingCode + '\'' +
                ", nfNamingCode='" + nfNamingCode + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", reportingEntityId='" + reportingEntityId + '\'' +
                ", reportingEntityName='" + reportingEntityName + '\'' +
                ", priority='" + priority + '\'' +
                ", startEpochMicrosec=" + startEpochMicrosec +
                ", lastEpochMicrosec=" + lastEpochMicrosec +
                ", sequence=" + sequence +
                '}';
    }
}

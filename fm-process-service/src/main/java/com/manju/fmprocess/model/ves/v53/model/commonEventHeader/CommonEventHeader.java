package com.manju.fmprocess.model.ves.v53.model.commonEventHeader;

public class CommonEventHeader {
    private double version;
    private String eventName;
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

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getNfcNamingCode() {
        return nfcNamingCode;
    }

    public void setNfcNamingCode(String nfcNamingCode) {
        this.nfcNamingCode = nfcNamingCode;
    }

    public String getNfNamingCode() {
        return nfNamingCode;
    }

    public void setNfNamingCode(String nfNamingCode) {
        this.nfNamingCode = nfNamingCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getReportingEntityId() {
        return reportingEntityId;
    }

    public void setReportingEntityId(String reportingEntityId) {
        this.reportingEntityId = reportingEntityId;
    }

    public String getReportingEntityName() {
        return reportingEntityName;
    }

    public void setReportingEntityName(String reportingEntityName) {
        this.reportingEntityName = reportingEntityName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getStartEpochMicrosec() {
        return startEpochMicrosec;
    }

    public void setStartEpochMicrosec(long startEpochMicrosec) {
        this.startEpochMicrosec = startEpochMicrosec;
    }

    public long getLastEpochMicrosec() {
        return lastEpochMicrosec;
    }

    public void setLastEpochMicrosec(long lastEpochMicrosec) {
        this.lastEpochMicrosec = lastEpochMicrosec;
    }

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

package com.manju.ecomp.model.ves.v5.heartbeat;

public class HeartbeatFields {
    private double heartbeatFieldsVersion;
    private int heartbeatInterval;

    public HeartbeatFields() {
    }

    public double getHeartbeatFieldsVersion() {
        return heartbeatFieldsVersion;
    }

    public void setHeartbeatFieldsVersion(double heartbeatFieldsVersion) {
        this.heartbeatFieldsVersion = heartbeatFieldsVersion;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    @Override
    public String toString() {
        return "HeartbeatFields{" +
                "heartbeatFieldsVersion=" + heartbeatFieldsVersion +
                ", heartbeatInterval=" + heartbeatInterval +
                '}';
    }
}

package com.manju.ecomp.model.ves.v5.measurement;

public class CpuUsage {
    private String cpuIdentifier;
    private double cpuIdle;
    private double cpuUsageInterrupt;
    private double cpuUsageNice;
    private double cpuUsageSoftIrq;
    private double cpuUsageSteal;
    private double cpuUsageSystem;
    private double cpuUsageUser;
    private double cpuWait;
    private double percentUsage;

    public CpuUsage() {
    }

    public String getCpuIdentifier() {
        return cpuIdentifier;
    }

    public void setCpuIdentifier(String cpuIdentifier) {
        this.cpuIdentifier = cpuIdentifier;
    }

    public double getCpuIdle() {
        return cpuIdle;
    }

    public void setCpuIdle(double cpuIdle) {
        this.cpuIdle = cpuIdle;
    }

    public double getCpuUsageInterrupt() {
        return cpuUsageInterrupt;
    }

    public void setCpuUsageInterrupt(double cpuUsageInterrupt) {
        this.cpuUsageInterrupt = cpuUsageInterrupt;
    }

    public double getCpuUsageNice() {
        return cpuUsageNice;
    }

    public void setCpuUsageNice(double cpuUsageNice) {
        this.cpuUsageNice = cpuUsageNice;
    }

    public double getCpuUsageSoftIrq() {
        return cpuUsageSoftIrq;
    }

    public void setCpuUsageSoftIrq(double cpuUsageSoftIrq) {
        this.cpuUsageSoftIrq = cpuUsageSoftIrq;
    }

    public double getCpuUsageSteal() {
        return cpuUsageSteal;
    }

    public void setCpuUsageSteal(double cpuUsageSteal) {
        this.cpuUsageSteal = cpuUsageSteal;
    }

    public double getCpuUsageSystem() {
        return cpuUsageSystem;
    }

    public void setCpuUsageSystem(double cpuUsageSystem) {
        this.cpuUsageSystem = cpuUsageSystem;
    }

    public double getCpuUsageUser() {
        return cpuUsageUser;
    }

    public void setCpuUsageUser(double cpuUsageUser) {
        this.cpuUsageUser = cpuUsageUser;
    }

    public double getCpuWait() {
        return cpuWait;
    }

    public void setCpuWait(double cpuWait) {
        this.cpuWait = cpuWait;
    }

    public double getPercentUsage() {
        return percentUsage;
    }

    public void setPercentUsage(double percentUsage) {
        this.percentUsage = percentUsage;
    }

    @Override
    public String toString() {
        return "CpuUsage{" +
                "cpuIdentifier='" + cpuIdentifier + '\'' +
                ", cpuIdle=" + cpuIdle +
                ", cpuUsageInterrupt=" + cpuUsageInterrupt +
                ", cpuUsageNice=" + cpuUsageNice +
                ", cpuUsageSoftIrq=" + cpuUsageSoftIrq +
                ", cpuUsageSteal=" + cpuUsageSteal +
                ", cpuUsageSystem=" + cpuUsageSystem +
                ", cpuUsageUser=" + cpuUsageUser +
                ", cpuWait=" + cpuWait +
                ", percentUsage=" + percentUsage +
                '}';
    }
}

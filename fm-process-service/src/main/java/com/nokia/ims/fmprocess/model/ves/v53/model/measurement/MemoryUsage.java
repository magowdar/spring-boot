package com.nokia.ims.fmprocess.model.ves.v53.model.measurement;

public class MemoryUsage {
    private double memoryBuffered;
    private double memoryCached;
    private double memoryConfigured;
    private double memoryFree;
    private double memorySlabRecl;
    private double memorySlabUnrecl;
    private double memoryUsed;
    private String vmIdentifier;

    public MemoryUsage() {
    }

    public double getMemoryBuffered() {
        return memoryBuffered;
    }

    public void setMemoryBuffered(double memoryBuffered) {
        this.memoryBuffered = memoryBuffered;
    }

    public double getMemoryCached() {
        return memoryCached;
    }

    public void setMemoryCached(double memoryCached) {
        this.memoryCached = memoryCached;
    }

    public double getMemoryConfigured() {
        return memoryConfigured;
    }

    public void setMemoryConfigured(double memoryConfigured) {
        this.memoryConfigured = memoryConfigured;
    }

    public double getMemoryFree() {
        return memoryFree;
    }

    public void setMemoryFree(double memoryFree) {
        this.memoryFree = memoryFree;
    }

    public double getMemorySlabRecl() {
        return memorySlabRecl;
    }

    public void setMemorySlabRecl(double memorySlabRecl) {
        this.memorySlabRecl = memorySlabRecl;
    }

    public double getMemorySlabUnrecl() {
        return memorySlabUnrecl;
    }

    public void setMemorySlabUnrecl(double memorySlabUnrecl) {
        this.memorySlabUnrecl = memorySlabUnrecl;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getVmIdentifier() {
        return vmIdentifier;
    }

    public void setVmIdentifier(String vmIdentifier) {
        this.vmIdentifier = vmIdentifier;
    }

    @Override
    public String toString() {
        return "MemoryUsage{" +
                "memoryBuffered=" + memoryBuffered +
                ", memoryCached=" + memoryCached +
                ", memoryConfigured=" + memoryConfigured +
                ", memoryFree=" + memoryFree +
                ", memorySlabRecl=" + memorySlabRecl +
                ", memorySlabUnrecl=" + memorySlabUnrecl +
                ", memoryUsed=" + memoryUsed +
                ", vmIdentifier='" + vmIdentifier + '\'' +
                '}';
    }
}

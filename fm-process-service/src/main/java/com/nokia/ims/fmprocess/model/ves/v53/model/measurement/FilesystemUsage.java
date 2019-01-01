package com.nokia.ims.fmprocess.model.ves.v53.model.measurement;

public class FilesystemUsage {
    private String filesystemName;
    private double blockConfigured;
    private double blockIops;
    private double blockUsed;
    private double ephemeralConfigured;
    private double ephemeralIops;
    private double ephemeralUsed;

    public FilesystemUsage() {
    }

    public String getFilesystemName() {
        return filesystemName;
    }

    public void setFilesystemName(String filesystemName) {
        this.filesystemName = filesystemName;
    }

    public double getBlockConfigured() {
        return blockConfigured;
    }

    public void setBlockConfigured(double blockConfigured) {
        this.blockConfigured = blockConfigured;
    }

    public double getBlockIops() {
        return blockIops;
    }

    public void setBlockIops(double blockIops) {
        this.blockIops = blockIops;
    }

    public double getBlockUsed() {
        return blockUsed;
    }

    public void setBlockUsed(double blockUsed) {
        this.blockUsed = blockUsed;
    }

    public double getEphemeralConfigured() {
        return ephemeralConfigured;
    }

    public void setEphemeralConfigured(double ephemeralConfigured) {
        this.ephemeralConfigured = ephemeralConfigured;
    }

    public double getEphemeralIops() {
        return ephemeralIops;
    }

    public void setEphemeralIops(double ephemeralIops) {
        this.ephemeralIops = ephemeralIops;
    }

    public double getEphemeralUsed() {
        return ephemeralUsed;
    }

    public void setEphemeralUsed(double ephemeralUsed) {
        this.ephemeralUsed = ephemeralUsed;
    }

    @Override
    public String toString() {
        return "FilesystemUsage{" +
                "filesystemName='" + filesystemName + '\'' +
                ", blockConfigured=" + blockConfigured +
                ", blockIops=" + blockIops +
                ", blockUsed=" + blockUsed +
                ", ephemeralConfigured=" + ephemeralConfigured +
                ", ephemeralIops=" + ephemeralIops +
                ", ephemeralUsed=" + ephemeralUsed +
                '}';
    }
}

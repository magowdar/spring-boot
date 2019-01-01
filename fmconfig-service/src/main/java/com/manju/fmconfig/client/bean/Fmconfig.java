package com.manju.fmconfig.client.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fmconfig-service")
public class Fmconfig {

    private String databasePathEcomp;
    private String databasePathEsymac;
    private String databaseDriver;
    private String databasePassword;
    private String lastSeqNumFile;
    private int historySyncLimit;
    private int completeHistorySyncLimit;
    private int tspRercoverLimit;
    private int maxQueueSize;
    private String ecrExtPattern;
    private String ddrExtPattern;
    private String ecrPath1;
    private String ecrPath2;
    private String adaptationsPath;
    private String adaptationsTxt;
    private String adaptationFileExt;
    private int maxTries;
    private String esymacDeployXML;
    private String esymacDeployClassName;
    private String encryptDecryptBinPath;
    private String heartbeatBufferPath;
    private String heartbeatBufferDirPath;

    public Fmconfig() {
    }

    public String getDatabasePathEcomp() {
        return databasePathEcomp;
    }

    public void setDatabasePathEcomp(String databasePathEcomp) {
        this.databasePathEcomp = databasePathEcomp;
    }

    public String getDatabasePathEsymac() {
        return databasePathEsymac;
    }

    public void setDatabasePathEsymac(String databasePathEsymac) {
        this.databasePathEsymac = databasePathEsymac;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getLastSeqNumFile() {
        return lastSeqNumFile;
    }

    public void setLastSeqNumFile(String lastSeqNumFile) {
        this.lastSeqNumFile = lastSeqNumFile;
    }

    public int getHistorySyncLimit() {
        return historySyncLimit;
    }

    public void setHistorySyncLimit(int historySyncLimit) {
        this.historySyncLimit = historySyncLimit;
    }

    public int getCompleteHistorySyncLimit() {
        return completeHistorySyncLimit;
    }

    public void setCompleteHistorySyncLimit(int completeHistorySyncLimit) {
        this.completeHistorySyncLimit = completeHistorySyncLimit;
    }

    public int getTspRercoverLimit() {
        return tspRercoverLimit;
    }

    public void setTspRercoverLimit(int tspRercoverLimit) {
        this.tspRercoverLimit = tspRercoverLimit;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public String getEcrExtPattern() {
        return ecrExtPattern;
    }

    public void setEcrExtPattern(String ecrExtPattern) {
        this.ecrExtPattern = ecrExtPattern;
    }

    public String getDdrExtPattern() {
        return ddrExtPattern;
    }

    public void setDdrExtPattern(String ddrExtPattern) {
        this.ddrExtPattern = ddrExtPattern;
    }

    public String getEcrPath1() {
        return ecrPath1;
    }

    public void setEcrPath1(String ecrPath1) {
        this.ecrPath1 = ecrPath1;
    }

    public String getEcrPath2() {
        return ecrPath2;
    }

    public void setEcrPath2(String ecrPath2) {
        this.ecrPath2 = ecrPath2;
    }

    public String getAdaptationsPath() {
        return adaptationsPath;
    }

    public void setAdaptationsPath(String adaptationsPath) {
        this.adaptationsPath = adaptationsPath;
    }

    public String getAdaptationsTxt() {
        return adaptationsTxt;
    }

    public void setAdaptationsTxt(String adaptationsTxt) {
        this.adaptationsTxt = adaptationsTxt;
    }

    public String getAdaptationFileExt() {
        return adaptationFileExt;
    }

    public void setAdaptationFileExt(String adaptationFileExt) {
        this.adaptationFileExt = adaptationFileExt;
    }

    public int getMaxTries() {
        return maxTries;
    }

    public void setMaxTries(int maxTries) {
        this.maxTries = maxTries;
    }

    public String getEsymacDeployXML() {
        return esymacDeployXML;
    }

    public void setEsymacDeployXML(String esymacDeployXML) {
        this.esymacDeployXML = esymacDeployXML;
    }

    public String getEsymacDeployClassName() {
        return esymacDeployClassName;
    }

    public void setEsymacDeployClassName(String esymacDeployClassName) {
        this.esymacDeployClassName = esymacDeployClassName;
    }

    public String getEncryptDecryptBinPath() {
        return encryptDecryptBinPath;
    }

    public void setEncryptDecryptBinPath(String encryptDecryptBinPath) {
        this.encryptDecryptBinPath = encryptDecryptBinPath;
    }

    public String getHeartbeatBufferPath() {
        return heartbeatBufferPath;
    }

    public void setHeartbeatBufferPath(String heartbeatBufferPath) {
        this.heartbeatBufferPath = heartbeatBufferPath;
    }

    public String getHeartbeatBufferDirPath() {
        return heartbeatBufferDirPath;
    }

    public void setHeartbeatBufferDirPath(String heartbeatBufferDirPath) {
        this.heartbeatBufferDirPath = heartbeatBufferDirPath;
    }

    @Override
    public String toString() {
        return "Fmconfig{" +
                "databasePathEcomp='" + databasePathEcomp + '\'' +
                ", databasePathEsymac='" + databasePathEsymac + '\'' +
                ", databaseDriver='" + databaseDriver + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", lastSeqNumFile='" + lastSeqNumFile + '\'' +
                ", historySyncLimit=" + historySyncLimit +
                ", completeHistorySyncLimit=" + completeHistorySyncLimit +
                ", tspRercoverLimit=" + tspRercoverLimit +
                ", maxQueueSize=" + maxQueueSize +
                ", ecrExtPattern='" + ecrExtPattern + '\'' +
                ", ddrExtPattern='" + ddrExtPattern + '\'' +
                ", ecrPath1='" + ecrPath1 + '\'' +
                ", ecrPath2='" + ecrPath2 + '\'' +
                ", adaptationsPath='" + adaptationsPath + '\'' +
                ", adaptationsTxt='" + adaptationsTxt + '\'' +
                ", adaptationFileExt='" + adaptationFileExt + '\'' +
                ", maxTries=" + maxTries +
                ", esymacDeployXML='" + esymacDeployXML + '\'' +
                ", esymacDeployClassName='" + esymacDeployClassName + '\'' +
                ", encryptDecryptBinPath='" + encryptDecryptBinPath + '\'' +
                ", heartbeatBufferPath='" + heartbeatBufferPath + '\'' +
                ", heartbeatBufferDirPath='" + heartbeatBufferDirPath + '\'' +
                '}';
    }
}

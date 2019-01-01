package com.manju.fmprocess.model.ves.v53.model.measurement;

import com.manju.fmprocess.model.ves.v53.model.common.Field;
import com.manju.fmprocess.model.ves.v53.model.common.NamedArrayOfFields;

import java.util.List;

public class MeasurementsForVfScalingFields {
    private double measurementsForVfScalingVersion;
    private NamedArrayOfFields additionalMeasurements;
    private List<Field> additionalFields;
    private int concurrentSessions;
    private int configuredEntities;
    private List<CpuUsage> cpuUsageArray;
    private List<DiskUsage> diskUsageArray;
    private List<FilesystemUsage> filesystemUsageArray;
    private List<FeaturesInUse> featureUsageArray;
    private List<LatencyBucketMeasure> latencyDistribution;
    private int meanRequestLatency;
    private int measurementInterval;
    private List<MemoryUsage> memoryUsageArray;
    private int numberOfMediaPortsInUse;
    private int requestRate;
    private int vnfcScalingMetric;
    private List<VNicPerformance> vNicPerformanceArray;

    public MeasurementsForVfScalingFields() {
    }

    public double getMeasurementsForVfScalingVersion() {
        return measurementsForVfScalingVersion;
    }

    public void setMeasurementsForVfScalingVersion(double measurementsForVfScalingVersion) {
        this.measurementsForVfScalingVersion = measurementsForVfScalingVersion;
    }

    public NamedArrayOfFields getAdditionalMeasurements() {
        return additionalMeasurements;
    }

    public void setAdditionalMeasurements(NamedArrayOfFields additionalMeasurements) {
        this.additionalMeasurements = additionalMeasurements;
    }

    public List<Field> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(List<Field> additionalFields) {
        this.additionalFields = additionalFields;
    }

    public int getConcurrentSessions() {
        return concurrentSessions;
    }

    public void setConcurrentSessions(int concurrentSessions) {
        this.concurrentSessions = concurrentSessions;
    }

    public int getConfiguredEntities() {
        return configuredEntities;
    }

    public void setConfiguredEntities(int configuredEntities) {
        this.configuredEntities = configuredEntities;
    }

    public List<CpuUsage> getCpuUsageArray() {
        return cpuUsageArray;
    }

    public void setCpuUsageArray(List<CpuUsage> cpuUsageArray) {
        this.cpuUsageArray = cpuUsageArray;
    }

    public List<DiskUsage> getDiskUsageArray() {
        return diskUsageArray;
    }

    public void setDiskUsageArray(List<DiskUsage> diskUsageArray) {
        this.diskUsageArray = diskUsageArray;
    }

    public List<FilesystemUsage> getFilesystemUsageArray() {
        return filesystemUsageArray;
    }

    public void setFilesystemUsageArray(List<FilesystemUsage> filesystemUsageArray) {
        this.filesystemUsageArray = filesystemUsageArray;
    }

    public List<LatencyBucketMeasure> getLatencyDistribution() {
        return latencyDistribution;
    }

    public void setLatencyDistribution(List<LatencyBucketMeasure> latencyDistribution) {
        this.latencyDistribution = latencyDistribution;
    }

    public int getMeanRequestLatency() {
        return meanRequestLatency;
    }

    public void setMeanRequestLatency(int meanRequestLatency) {
        this.meanRequestLatency = meanRequestLatency;
    }

    public int getMeasurementInterval() {
        return measurementInterval;
    }

    public void setMeasurementInterval(int measurementInterval) {
        this.measurementInterval = measurementInterval;
    }

    public List<MemoryUsage> getMemoryUsageArray() {
        return memoryUsageArray;
    }

    public void setMemoryUsageArray(List<MemoryUsage> memoryUsageArray) {
        this.memoryUsageArray = memoryUsageArray;
    }

    public int getNumberOfMediaPortsInUse() {
        return numberOfMediaPortsInUse;
    }

    public void setNumberOfMediaPortsInUse(int numberOfMediaPortsInUse) {
        this.numberOfMediaPortsInUse = numberOfMediaPortsInUse;
    }

    public int getRequestRate() {
        return requestRate;
    }

    public void setRequestRate(int requestRate) {
        this.requestRate = requestRate;
    }

    public int getVnfcScalingMetric() {
        return vnfcScalingMetric;
    }

    public void setVnfcScalingMetric(int vnfcScalingMetric) {
        this.vnfcScalingMetric = vnfcScalingMetric;
    }

    public List<VNicPerformance> getvNicPerformanceArray() {
        return vNicPerformanceArray;
    }

    public void setvNicPerformanceArray(List<VNicPerformance> vNicPerformanceArray) {
        this.vNicPerformanceArray = vNicPerformanceArray;
    }

    @Override
    public String toString() {
        return "MeasurementsForVfScalingFields{" +
                "measurementsForVfScalingVersion=" + measurementsForVfScalingVersion +
                ", additionalMeasurements=" + additionalMeasurements +
                ", additionalFields=" + additionalFields +
                ", concurrentSessions=" + concurrentSessions +
                ", configuredEntities=" + configuredEntities +
                ", cpuUsageArray=" + cpuUsageArray +
                ", diskUsageArray=" + diskUsageArray +
                ", filesystemUsageArray=" + filesystemUsageArray +
                ", latencyDistribution=" + latencyDistribution +
                ", meanRequestLatency=" + meanRequestLatency +
                ", measurementInterval=" + measurementInterval +
                ", memoryUsageArray=" + memoryUsageArray +
                ", numberOfMediaPortsInUse=" + numberOfMediaPortsInUse +
                ", requestRate=" + requestRate +
                ", vnfcScalingMetric=" + vnfcScalingMetric +
                ", vNicPerformanceArray=" + vNicPerformanceArray +
                '}';
    }
}

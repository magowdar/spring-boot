package com.manju.fmprocess.model.ves.v53.model.measurement;

public class LatencyBucketMeasure {

    private int countsInTheBucket;
    private int highEndOfLatencyBucket;
    private int lowEndOfLatencyBucket;

    public LatencyBucketMeasure() {
    }

    public int getCountsInTheBucket() {
        return countsInTheBucket;
    }

    public void setCountsInTheBucket(int countsInTheBucket) {
        this.countsInTheBucket = countsInTheBucket;
    }

    public int getHighEndOfLatencyBucket() {
        return highEndOfLatencyBucket;
    }

    public void setHighEndOfLatencyBucket(int highEndOfLatencyBucket) {
        this.highEndOfLatencyBucket = highEndOfLatencyBucket;
    }

    public int getLowEndOfLatencyBucket() {
        return lowEndOfLatencyBucket;
    }

    public void setLowEndOfLatencyBucket(int lowEndOfLatencyBucket) {
        this.lowEndOfLatencyBucket = lowEndOfLatencyBucket;
    }

    @Override
    public String toString() {
        return "LatencyBucketMeasure{" +
                "countsInTheBucket=" + countsInTheBucket +
                ", highEndOfLatencyBucket=" + highEndOfLatencyBucket +
                ", lowEndOfLatencyBucket=" + lowEndOfLatencyBucket +
                '}';
    }
}

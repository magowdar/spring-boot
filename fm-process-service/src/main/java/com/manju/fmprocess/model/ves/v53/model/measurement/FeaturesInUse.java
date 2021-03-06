package com.manju.fmprocess.model.ves.v53.model.measurement;

public class FeaturesInUse {
    private String featureIdentifer;
    private int featureUtilization;

    public FeaturesInUse() {
    }

    public String getFeatureIdentifer() {
        return featureIdentifer;
    }

    public void setFeatureIdentifer(String featureIdentifer) {
        this.featureIdentifer = featureIdentifer;
    }

    public int getFeatureUtilization() {
        return featureUtilization;
    }

    public void setFeatureUtilization(int featureUtilization) {
        this.featureUtilization = featureUtilization;
    }

    @Override
    public String toString() {
        return "FeaturesInUse{" +
                "featureIdentifer='" + featureIdentifer + '\'' +
                ", featureUtilization=" + featureUtilization +
                '}';
    }
}

package com.manju.ecomp.model.ves.v5.measurement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.manju.ecomp.model.ves.v5.commonEventHeader.CommonEventHeader;

@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonTypeName("event")
public class MeasurementEvent {
    private CommonEventHeader commonEventHeader;
    private MeasurementsForVfScalingFields measurementsForVfScalingFields;

    public MeasurementEvent() {
    }

    public CommonEventHeader getCommonEventHeader() {
        return commonEventHeader;
    }

    public void setCommonEventHeader(CommonEventHeader commonEventHeader) {
        this.commonEventHeader = commonEventHeader;
    }

    public MeasurementsForVfScalingFields getMeasurementsForVfScalingFields() {
        return measurementsForVfScalingFields;
    }

    public void setMeasurementsForVfScalingFields(MeasurementsForVfScalingFields measurementsForVfScalingFields) {
        this.measurementsForVfScalingFields = measurementsForVfScalingFields;
    }

    @Override
    public String toString() {
        return "MeasurementEvent{" +
                "commonEventHeader=" + commonEventHeader +
                ", measurementsForVfScalingFields=" + measurementsForVfScalingFields +
                '}';
    }
}

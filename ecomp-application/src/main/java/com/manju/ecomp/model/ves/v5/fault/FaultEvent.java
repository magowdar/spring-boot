package com.manju.ecomp.model.ves.v5.fault;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.manju.ecomp.model.ves.v5.commonEventHeader.CommonEventHeader;

@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonTypeName("event")
public class FaultEvent {
    CommonEventHeader commonEventHeader;
    FaultFields faultFields;

    public FaultEvent() {
    }

    public CommonEventHeader getCommonEventHeader() {
        return commonEventHeader;
    }

    public void setCommonEventHeader(CommonEventHeader commonEventHeader) {
        this.commonEventHeader = commonEventHeader;
    }

    public FaultFields getFaultFields() {
        return faultFields;
    }

    public void setFaultFields(FaultFields faultFields) {
        this.faultFields = faultFields;
    }

    @Override
    public String toString() {
        return "FaultEvent{" +
                "commonEventHeader=" + commonEventHeader +
                ", faultFields=" + faultFields +
                '}';
    }
}

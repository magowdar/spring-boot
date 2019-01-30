package com.manju.ecomp.model.ves.v5;

import com.manju.ecomp.model.ves.v5.commonEventHeader.CommonEventHeader;
import com.manju.ecomp.model.ves.v5.fault.FaultFields;

import javax.validation.Valid;

public class FaultEvent {

    public FaultEvent() {
    }

    @Valid
    CommonEventHeader commonEventHeader;

    @Valid
    FaultFields faultFields;

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
}

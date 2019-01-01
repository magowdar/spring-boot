package com.nokia.ims.fmprocess.model.ves.v53.model;

import com.nokia.ims.fmprocess.model.ves.v53.model.commonEventHeader.CommonEventHeader;
import com.nokia.ims.fmprocess.model.ves.v53.model.fault.FaultFields;

public class EcompEvent {
    CommonEventHeader commonEventHeader;
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

    @Override
    public String toString() {
        return "EcompEvent{" +
                "commonEventHeader=" + commonEventHeader +
                ", faultFields=" + faultFields +
                '}';
    }
}

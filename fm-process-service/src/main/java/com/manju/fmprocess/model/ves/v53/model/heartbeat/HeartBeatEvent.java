package com.manju.fmprocess.model.ves.v53.model.heartbeat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.manju.fmprocess.model.ves.v53.model.commonEventHeader.CommonEventHeader;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("event")
public class HeartBeatEvent {
    CommonEventHeader commonEventHeader;
    HeartbeatFields heartbeatFields;

    public HeartBeatEvent() {
    }

    public CommonEventHeader getCommonEventHeader() {
        return commonEventHeader;
    }

    public void setCommonEventHeader(CommonEventHeader commonEventHeader) {
        this.commonEventHeader = commonEventHeader;
    }

    public HeartbeatFields getHeartbeatFields() {
        return heartbeatFields;
    }

    public void setHeartbeatFields(HeartbeatFields heartbeatFields) {
        this.heartbeatFields = heartbeatFields;
    }
}

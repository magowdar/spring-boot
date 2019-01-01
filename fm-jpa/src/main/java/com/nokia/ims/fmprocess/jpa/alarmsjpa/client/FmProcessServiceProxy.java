package com.nokia.ims.fmprocess.jpa.alarmsjpa.client;

import com.nokia.ims.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "fm-process-ecomp-service",url = "localhost:9002")

public interface FmProcessServiceProxy {
    @PostMapping("/fmprocess/processAlarm-to-ecomp")
    public String postAlarm(@RequestBody AlarmEvent alarmEvent);
}

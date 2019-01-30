package com.manju.fmprocess.jpa.alarmsjpa.client;

import com.manju.fmprocess.jpa.alarmsjpa.model.AlarmEvent;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "fm-process-ecomp-service")
@RibbonClient(name = "fm-process-ecomp-service")

public interface FmProcessServiceProxy {
    @PostMapping("/fmprocess/processAlarm-to-ecomp")
    public String postAlarm(@RequestBody AlarmEvent alarmEvent);
}

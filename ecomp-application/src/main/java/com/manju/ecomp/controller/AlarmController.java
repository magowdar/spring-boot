package com.manju.ecomp.controller;


import com.fasterxml.jackson.databind.node.TextNode;
import com.manju.ecomp.service.EventValidationService;
import com.manju.ecomp.service.EventValidatorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AlarmController {
    Logger logger = LoggerFactory.getLogger(AlarmController.class);

    private static final int VES5_VERSION = 5;
    private static final int VES7_VERSION = 7;

    @Autowired
    EventValidatorAdapter eventValidationService;

    @PostMapping(value = "/eventListener/v5")
    public String receiveEventForV5(@RequestBody String event){
        logger.info(" FaultEvent Details : " + event);
        eventValidationService.validateEvent(VES5_VERSION,event);
        return "FaultEvent received" + event;
    }

    @PostMapping("/eventListener/v7")
    public String receiveEventForV7(@RequestBody String event){
        return "FaultEvent received";
    }

    @PostMapping("/eventListener/v5/eventBatch")
    public String receiveEventBatchForV5(@RequestBody String event){
        return "FaultEvent received";
    }

    @PostMapping("/eventListener/v7/eventBatch")
    public String receiveEventBatchForV7(@RequestBody String event){
        return "FaultEvent received";
    }


}

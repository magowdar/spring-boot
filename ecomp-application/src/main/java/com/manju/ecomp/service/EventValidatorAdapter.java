package com.manju.ecomp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class EventValidatorAdapter implements EventValidationService{

    @Autowired
    private EventValidationServiceV5 eventValidationServiceV5;

    @Override
    public boolean validateEvent(int release, String event) {

        if(release == 5){
            return eventValidationServiceV5.validateEvent(release,event);
        }
        return false;
    }
}

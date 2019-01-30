package com.manju.ecomp.service;


import com.manju.ecomp.exception.InvalidEventException;
import com.manju.ecomp.model.ves.v5.EventV5;
import com.manju.ecomp.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
@Validated
public class EventValidationServiceV5 implements EventValidationService{

    @Autowired
    Validator validator;

    private static Logger logger = LoggerFactory.getLogger(EventValidationServiceV5.class);

    @Override
    public boolean validateEvent(int release, String event) {
        EventV5 evtObject = JacksonUtil.getEvent(release,event);
        Set<ConstraintViolation<EventV5>> validatorConstraints = validator.validate(evtObject);
        logger.info(" Constaints Size :" + validatorConstraints.size());
        if(validatorConstraints.size() > 0){
            String errorMessage = null;
            for(ConstraintViolation constraint : validatorConstraints){
                errorMessage += "::" + constraint.toString();
            }
            throw new InvalidEventException(errorMessage);
        }
        logger.info("FaultEvent received with Details :" + evtObject.getEvent().getCommonEventHeader().getEventId());
        return true;
    }
}

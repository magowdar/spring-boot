package com.manju.ecomp.service;

import org.springframework.stereotype.Component;

@Component
public interface EventValidationService {

    public boolean validateEvent(int release, String event);
}

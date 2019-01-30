package com.manju.ecomp.model.ves.v5;

import javax.validation.Valid;

public class EventV5 {

    @Valid
    private FaultEvent event;

    public FaultEvent getEvent() {
        return event;
    }

    public void setEvent(FaultEvent event) {
        this.event = event;
    }
}

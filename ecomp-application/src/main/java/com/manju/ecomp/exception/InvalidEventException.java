package com.manju.ecomp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEventException extends RuntimeException {

    public InvalidEventException(String message) {

        super(message);
    }
}

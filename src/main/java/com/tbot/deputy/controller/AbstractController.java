package com.tbot.deputy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tbot.deputy.service.ServiceException;

public class AbstractController implements ControllerConstants {
    static final String MESSAGE_SERVICE_EXCEPTION = "Internal service exception occcured!";  
    
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServiceException(ServiceException ex) {
        return MESSAGE_SERVICE_EXCEPTION;
    }
}

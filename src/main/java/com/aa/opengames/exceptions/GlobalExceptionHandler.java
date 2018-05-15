package com.aa.opengames.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @MessageExceptionHandler(value = RuntimeException.class)
    public void defaultErrorHandler(RuntimeException e) {
        LOGGER.warn(e.getMessage());
    }
}

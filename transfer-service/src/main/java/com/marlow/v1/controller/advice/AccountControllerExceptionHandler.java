package com.marlow.v1.controller.advice;

import com.marlow.v1.controller.AccountController;
import com.marlow.v1.exceptions.AccountNotFoundException;
import com.marlow.v1.exceptions.KafkaStreamsUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ControllerAdvice(assignableTypes = AccountController.class)
@Slf4j
public class AccountControllerExceptionHandler {

    @ExceptionHandler(KafkaStreamsUnavailableException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public void handleAccountException(KafkaStreamsUnavailableException ex) {
        log.error("KafkaStreams is null. Exception : ", ex);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleAccountException(AccountNotFoundException ex) {
        log.error("No such account. Exception : ", ex);
    }
}

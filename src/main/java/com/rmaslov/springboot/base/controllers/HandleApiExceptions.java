package com.rmaslov.springboot.base.controllers;

import com.rmaslov.springboot.base.api.response.ErrorResponse;
import com.rmaslov.springboot.base.api.response.OkResponse;
import com.rmaslov.springboot.user.exception.UserExistException;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleApiExceptions extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatusHttp());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> conflict(ChangeSetPersister.NotFoundException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("NotFoundException", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistException(UserExistException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("UserExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Object> userNotExistException(UserNotExistException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("UserNotExistException", HttpStatus.BAD_REQUEST));
    }



}

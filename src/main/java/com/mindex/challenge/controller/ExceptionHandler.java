package com.mindex.challenge.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/*
Want to return 204 in the event of no response from repository for user convenience. It's also nice to have more detailed
error reporting depending on the nature of the api, but I will skip this as a time consideration.
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "No content available", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }
}

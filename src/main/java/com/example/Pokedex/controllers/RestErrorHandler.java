package com.example.Pokedex.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> handleNotFound(HttpClientErrorException.NotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }
}

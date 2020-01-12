package com.n26.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class SystemControllerAdvice {
    @ExceptionHandler({JsonParseException.class, MismatchedInputException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    void handleInvalidJson(HttpServletResponse response) {
    }

    @ExceptionHandler({InvalidFormatException.class, UnrecognizedPropertyException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    void handleNonParsableData(HttpServletResponse response) {
    }
}

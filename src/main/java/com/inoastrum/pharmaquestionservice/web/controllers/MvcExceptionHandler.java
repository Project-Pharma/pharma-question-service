package com.inoastrum.pharmaquestionservice.web.controllers;

import com.inoastrum.pharmaquestionservice.exceptions.ErrorObject;
import com.inoastrum.pharmaquestionservice.exceptions.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorObject> validationErrorHandler(ConstraintViolationException ex){
        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());

        ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));

        return new ResponseEntity<>(new ErrorObject(errorsList, ex.getClass()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({QuestionNotFoundException.class})
    public ResponseEntity<ErrorObject> notFoundHandler(Exception e) {
        return new ResponseEntity<>(new ErrorObject(List.of(e.getMessage()), e.getClass()), HttpStatus.NOT_FOUND);
    }
}

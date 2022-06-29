package com.ebs.marketplace.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity handleAccessDeniedException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<String> details = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        ErrorResponse error = new ErrorResponse("Datele introduse nu corespund cerintelor!", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity handleIllegalArgumentException(Exception ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMostSpecificCause().getMessage());
        ErrorResponse error = new ErrorResponse("Valorile introduse nu corespund cerintelor!", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}

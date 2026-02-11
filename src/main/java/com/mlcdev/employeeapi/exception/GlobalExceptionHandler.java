package com.mlcdev.employeeapi.exception;

import com.mlcdev.employeeapi.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.Instant;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomError> notFound(NotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> entityNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Invalid Data", request.getRequestURI());
        e.getBindingResult().getFieldErrors().stream().forEach(x -> error.addError(x.getField(), x.getDefaultMessage()));
        log.error("Not Valid Entity error: {}",e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationError> jsonError(HttpMessageNotReadableException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Invalid Data", request.getRequestURI());
        if (e.getCause() instanceof InvalidFormatException) {
            error.addError("role", "Unavailable Role. Available Roles: " + Arrays.toString(Role.values()));
        }
        log.error("Json Format error: {}",e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> dataBaseError(DataIntegrityViolationException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError err = new CustomError(Instant.now(), status.value() , "The request violates the Database integrity", request.getRequestURI());
        log.error("Database error: {}",e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomError> invalidParam(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), "Invalid Parameter", request.getRequestURI());
        log.error("Invalid Parameter error: {}",e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<CustomError> invalidProperty(PropertyReferenceException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), "Invalid property", request.getRequestURI());
        log.error("Invalid property error: {}", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> genericError(Exception e, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomError err = new CustomError(Instant.now(), status.value(), "Unexpected Error Occurred", request.getRequestURI());
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }


}

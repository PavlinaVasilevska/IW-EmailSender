package com.example.EmailSender.infrastructure.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Exception: {}", errorId, ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Please contact the administrator with the following error identifier: " + errorId);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Resource Not Found: {}", errorId, ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Resource not found. Please contact the administrator with the following error identifier: " + errorId);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - No Handler Found: {}", errorId, ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Endpoint not found. Please contact the administrator with the following error identifier: " + errorId);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Define more handlers as needed
}
package com.gearglobe.app.backend.configuration;

import com.gearglobe.app.backend.configuration.exception.AddressNotFoundException;
import com.gearglobe.app.backend.configuration.exception.ClientNotFoundException;
import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import com.gearglobe.app.backend.configuration.exception.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionHandler() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> clientNotFoundExceptionHandler(ClientNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> addressNotFoundExceptionHandler(AddressNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> incorrectPasswordExceptionHandler(IncorrectPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(IncorrectClientTypeDataException.class)
    public ResponseEntity<String> incorrectClientTypeDataExceptionHandler(IncorrectClientTypeDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}

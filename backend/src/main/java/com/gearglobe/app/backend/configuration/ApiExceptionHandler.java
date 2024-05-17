package com.gearglobe.app.backend.configuration;

import com.gearglobe.app.backend.configuration.exception.*;
import com.gearglobe.dto.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> entityNotFoundExceptionHandler() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, "Entity not found");
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> offerNotFoundExceptionHandler(OfferNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, exception.getMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> clientNotFoundExceptionHandler(ClientNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, exception.getMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> addressNotFoundExceptionHandler(AddressNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, exception.getMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> illegalArgumentExceptionHandler() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, "Invalid request");
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> incorrectPasswordExceptionHandler(IncorrectPasswordException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, exception.getMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(IncorrectClientTypeDataException.class)
    public ResponseEntity<ErrorResponseDTO> incorrectClientTypeDataExceptionHandler(IncorrectClientTypeDataException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO response = buildErrorResponse(httpStatus, exception.getMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    private static ErrorResponseDTO buildErrorResponse(HttpStatus status, String message) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();
    }
}

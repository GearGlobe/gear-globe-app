package com.gearglobe.app.backend.configuration.exception;

import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IncorrectPasswordException extends PersistenceException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}

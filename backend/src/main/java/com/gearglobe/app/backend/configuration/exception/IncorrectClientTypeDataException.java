package com.gearglobe.app.backend.configuration.exception;

import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IncorrectClientTypeDataException extends PersistenceException {
    public IncorrectClientTypeDataException(String message) {
        super(message);
    }
}

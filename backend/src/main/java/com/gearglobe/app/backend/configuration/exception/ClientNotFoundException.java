package com.gearglobe.app.backend.configuration.exception;

import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientNotFoundException extends PersistenceException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}

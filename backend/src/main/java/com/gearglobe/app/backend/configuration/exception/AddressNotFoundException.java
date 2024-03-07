package com.gearglobe.app.backend.configuration.exception;

import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddressNotFoundException extends PersistenceException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}

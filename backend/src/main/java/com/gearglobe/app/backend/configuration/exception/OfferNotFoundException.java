package com.gearglobe.app.backend.configuration.exception;

import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OfferNotFoundException extends PersistenceException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}

package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;

public interface OfferCreationStrategy<T extends CreateOfferRequestDTO> {
    Class<T> getSupportedType();
    Offer createOffer(T createOfferRequest, Long clientId);
}

package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateCarOfferRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CarOfferCreationStrategy implements OfferCreationStrategy<CreateCarOfferRequestDTO> {
    @Override
    public Class<CreateCarOfferRequestDTO> getSupportedType() {
        return CreateCarOfferRequestDTO.class;
    }

    @Override
    public Offer createOffer(CreateCarOfferRequestDTO createOfferRequest, Long clientId) {
        return CarOffer.createOffer(createOfferRequest, clientId);
    }
}
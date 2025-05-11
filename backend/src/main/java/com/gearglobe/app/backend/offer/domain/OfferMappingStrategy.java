package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;

public interface OfferMappingStrategy<T extends Offer, R extends OfferResponseDTO> {
    Class<T> getSupportedType();
    R map(T entity);
    T map(R dto);
}

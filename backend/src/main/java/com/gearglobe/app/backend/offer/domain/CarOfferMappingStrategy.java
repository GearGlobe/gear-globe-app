package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CarOfferResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CarOfferMappingStrategy implements OfferMappingStrategy<CarOffer, CarOfferResponseDTO> {
    @Override
    public Class<CarOffer> getSupportedType() {
        return CarOffer.class;
    }

    @Override
    public CarOfferResponseDTO map(CarOffer entity) {
        return OfferMapper.INSTANCE.map(entity);
    }

    @Override
    public CarOffer map(CarOfferResponseDTO dto) {
        return OfferMapper.INSTANCE.map(dto);
    }
}

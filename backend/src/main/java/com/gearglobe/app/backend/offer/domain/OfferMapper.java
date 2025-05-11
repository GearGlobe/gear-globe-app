package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CarOfferResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    OfferResponseDTO map(Offer entity);
    CarOfferResponseDTO map(CarOffer entity);
    CarOffer map(CarOfferResponseDTO dto);
}

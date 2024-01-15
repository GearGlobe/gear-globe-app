package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    OfferDTO offerToOfferDTO(Offer offer);
    Offer offerDTOToOffer(OfferDTO offerDTO);
}

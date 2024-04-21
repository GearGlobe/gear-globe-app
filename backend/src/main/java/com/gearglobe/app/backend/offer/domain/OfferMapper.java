package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    OfferResponseDTO map(Offer offer);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Offer map(OfferResponseDTO offerDTO);
    Offer map(CreateOfferRequestDTO createOfferRequestDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Offer map(UpdateOfferRequestDTO updateOfferRequestDTO);
}

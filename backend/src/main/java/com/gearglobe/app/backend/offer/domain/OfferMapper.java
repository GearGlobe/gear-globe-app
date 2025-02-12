package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferIdResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Mapper
interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    
    OfferResponseDTO map(Offer offer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    Offer map(OfferResponseDTO offerDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    Offer map(CreateOfferRequestDTO createOfferRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    Offer map(UpdateOfferRequestDTO updateOfferRequestDTO);

    OfferIdResponseDTO mapToOfferIdResponseDTO(Offer offer);
}

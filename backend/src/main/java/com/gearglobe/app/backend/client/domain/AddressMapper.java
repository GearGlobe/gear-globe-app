package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressRequestDTO;
import com.gearglobe.app.backend.client.api.dtos.AddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Address map(AddressRequestDTO addressRequestDTO);
    AddressResponseDTO map(Address address);
}

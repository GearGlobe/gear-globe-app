package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    Address map(AddressDTO addressDTO);
    AddressDTO map(Address address);
}

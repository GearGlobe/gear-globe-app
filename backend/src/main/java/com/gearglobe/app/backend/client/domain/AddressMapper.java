package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.AddressResponseDTO;
import com.gearglobe.dto.CreateAddressRequestDTO;
import com.gearglobe.dto.UpdateAddressRequestDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientMapper.class})
interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "country", source = "country")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "houseNumber", source = "houseNumber")
    @Mapping(target = "apartmentNumber", source = "apartmentNumber")
    Address map(CreateAddressRequestDTO createAddressRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "country", source = "country")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "houseNumber", source = "houseNumber")
    @Mapping(target = "apartmentNumber", source = "apartmentNumber")
    Address map(UpdateAddressRequestDTO updateAddressRequestDTO);

    AddressResponseDTO map(Address address);
}

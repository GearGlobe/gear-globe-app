package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.ClientResponseDTO;
import com.gearglobe.dto.CreateClientRequestDTO;
import com.gearglobe.dto.UpdateClientRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class})
interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    @Mapping(target = "address", source = "address")
    @Mapping(target = "clientRole", source = "role")
    @Mapping(target = "clientStatus", source = "status")
    ClientResponseDTO map(Client client);
    @Mapping(target = "address", source = "address")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    Client map(CreateClientRequestDTO clientDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    Client map(UpdateClientRequestDTO clientDTO);
}

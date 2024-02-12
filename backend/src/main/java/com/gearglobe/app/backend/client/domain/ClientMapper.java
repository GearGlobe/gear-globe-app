package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.ClientResponseDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientResponseDTO map(Client client);
    Client map(ClientRequestDTO clientDTO);
}

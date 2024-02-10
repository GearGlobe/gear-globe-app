package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.ClientGetDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    Client clientGetDTOToClient(ClientGetDTO clientDTO);
    ClientGetDTO clientToClientGetDTO(Client client);
    Client clientPostDTOToClient(ClientPostDTO clientDTO);
    ClientPostDTO clientToClientPostDTO(Client client);
}

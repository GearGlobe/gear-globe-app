package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientGetDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientPostDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

interface ClientService {
    List<ClientGetDTO> getAllClients();
    Optional<ClientGetDTO> getClientById(Long id);
    ClientPostDTO createClient(ClientPostDTO clientDTO, AddressDTO addressDTO);
    ClientPostDTO updateClient(ClientPostDTO clientDTO) throws EntityNotFoundException;
    ClientPostDTO updateClientAddress(Long id, AddressDTO addressDTO) throws EntityNotFoundException;
    ClientPostDTO changeClientPassword(Long id, String password) throws EntityNotFoundException;
    ClientPostDTO deactivateClient(Long id) throws EntityNotFoundException;
}

package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestUpdateDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientResponseDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

interface ClientService {
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO getClientById(Long id);
    ClientResponseDTO createClient(ClientRequestDTO clientDTO);
    ClientResponseDTO updateClient(Long id, ClientRequestUpdateDTO clientDTO) throws EntityNotFoundException;
    AddressDTO updateClientAddress(Long id, AddressDTO addressDTO) throws EntityNotFoundException;
    Long changeClientPassword(Long id, String password) throws EntityNotFoundException;
    Long deactivateClient(Long id) throws EntityNotFoundException;
}

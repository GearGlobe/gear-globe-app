package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

interface ClientService {
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO getClientById(Long id);
    ClientResponseDTO createClient(ClientRequestDTO clientDTO);
    ClientResponseDTO updateClient(Long id, ClientRequestUpdateDTO clientDTO) throws EntityNotFoundException;
    AddressResponseDTO updateClientAddress(Long id, AddressRequestDTO addressRequestDTO) throws EntityNotFoundException;
    Long changeClientPassword(Long id, String oldPasswordToCheck, String password) throws EntityNotFoundException;
    Long deactivateClient(Long id) throws EntityNotFoundException;
}

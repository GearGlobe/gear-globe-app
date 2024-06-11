package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.*;

import java.util.List;

interface ClientService {
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO getClientById(Long id);
    ClientResponseDTO createClient(CreateClientRequestDTO clientDTO);
    ClientResponseDTO updateClient(Long id, UpdateClientRequestDTO clientDTO);
    AddressResponseDTO updateClientAddress(Long id, UpdateAddressRequestDTO addressRequestDTO);
    ClientIdResponseDTO changeClientPassword(Long id, String oldPasswordToCheck, String password);
    ClientIdResponseDTO deactivateClient(Long id);
}

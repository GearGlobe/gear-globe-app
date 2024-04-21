package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferIdResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

interface OfferService {
    List<OfferResponseDTO> getAllOffers();
    OfferResponseDTO getOfferById(Long id);
    OfferResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO);
    OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) throws EntityNotFoundException;
    OfferIdResponseDTO archiveOffer(Long id);
}

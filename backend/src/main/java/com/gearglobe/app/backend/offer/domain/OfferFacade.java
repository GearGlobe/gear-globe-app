package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferIdResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import java.util.List;

public interface OfferFacade {
    List<OfferResponseDTO> getAllOffers();
    OfferResponseDTO getOfferById(Long id);
    OfferResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO);
    OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO);
    OfferIdResponseDTO archiveOffer(Long id);
}

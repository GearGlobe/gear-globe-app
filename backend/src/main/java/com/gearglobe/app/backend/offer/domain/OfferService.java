package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.*;

import java.util.List;

interface OfferService {
    List<CarOfferResponseDTO> getAllCarOffers();
    OfferResponseDTO getOfferById(Long id);
    OfferIdResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO);
    OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO);
    OfferIdResponseDTO archiveOffer(Long id);
}

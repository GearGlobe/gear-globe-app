package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;

interface OfferResponseCreator {
    OfferResponseDTO createOfferResponse(Offer offer);
}

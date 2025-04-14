package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.OfferTypeDTO;

interface OfferResponseCreator {
    OfferResponseDTO createOfferResponseByType(OfferTypeDTO offerType, Long offerId);
}

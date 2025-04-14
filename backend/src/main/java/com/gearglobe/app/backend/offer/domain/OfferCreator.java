package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;

import java.util.List;

interface OfferCreator {
    Offer createOffer(CreateOfferRequestDTO createOfferRequestDTO, Long clientId);

    List<CarOffer> getAllCarOffers();
}

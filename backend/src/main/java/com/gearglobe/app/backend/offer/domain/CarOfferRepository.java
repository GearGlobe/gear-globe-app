package com.gearglobe.app.backend.offer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CarOfferRepository extends JpaRepository<CarOffer, Long> {
    CarOffer findByOfferId(Long offerId);
}

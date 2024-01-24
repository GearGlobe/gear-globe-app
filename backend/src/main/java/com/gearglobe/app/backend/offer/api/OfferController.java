package com.gearglobe.app.backend.offer.api;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import com.gearglobe.app.backend.offer.domain.OfferFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(OfferController.OFFER_URL)
@RequiredArgsConstructor
class OfferController {
    public static final String OFFER_URL = "/api/offers";
    public final OfferFacade offerFacade;

    @GetMapping
    public List<OfferDTO> getAllOffers() {
        return offerFacade.getAllOffers();
    }

    @GetMapping("/{id}")
    public Optional<OfferDTO> getOfferById(@PathVariable Long id) {
        return offerFacade.getOfferById(id);
    }

    @PostMapping
    public OfferDTO createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        return offerFacade.createOffer(offerDTO);
    }

    @PutMapping("/{id}")
    public OfferDTO updateOffer(@Valid @RequestBody OfferDTO offerDTO) {
        return offerFacade.updateOffer(offerDTO);
    }

    @DeleteMapping("/{id}")
    public OfferDTO archiveOffer(@PathVariable Long id) {
        return offerFacade.archiveOffer(id);
    }
}

package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.configuration.exception.OfferNotFoundException;
import com.gearglobe.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO fix this
@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final OfferCreator offerCreator;
    private final OfferResponseCreator offerResponseCreator;

    @Override
    public List<CarOfferResponseDTO> getAllCarOffers() {
        return offerCreator.getAllCarOffers()
                .stream()
                .map(OfferMapper.INSTANCE::map)
                .toList();
    }

    //TODO zastanowic sie czy faktycznie potrzebna jest ta metoda?
    @Override
    public OfferResponseDTO getOfferById(Long id) {
        Offer offer = findOfferById(id);
        return offerResponseCreator.createOfferResponse(offer);
    }

    @Override
    public OfferIdResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO) {
        Long clientId = 666L; //TODO: Add the ID of the logged-in client
        Offer offer = offerCreator.createOffer(createOfferRequestDTO, clientId);
        return OfferIdResponseDTO.builder().id(offer.getId()).build();
    }

    @Override
    public OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
//        Offer offer = findOfferById(id);
//        offer.updateOffer(updateOfferRequestDTO);
//        offerRepository.save(offer);
//        return OfferMapper.INSTANCE.map(offer);
        return null;
    }

    @Override
    public OfferIdResponseDTO archiveOffer(Long id) {
//        Offer offer = findOfferById(id);
//        if (offer.isActiveOffer()) {
//            offer.archiveOffer();
//            offerRepository.save(offer);
//        }
//        return OfferIdResponseDTO.builder().id(id).build();
        return null;
    }

    private Offer findOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
    }
}

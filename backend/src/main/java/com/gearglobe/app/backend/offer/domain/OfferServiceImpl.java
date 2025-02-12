package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.configuration.exception.OfferNotFoundException;
import com.gearglobe.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public List<OfferResponseDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper.INSTANCE::map)
                .toList();
    }

    @Override
    public OfferResponseDTO getOfferById(Long id) {
        Offer offer = findOfferById(id);
        return OfferMapper.INSTANCE.map(offer);
    }

    @Override
    public OfferResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO) {
        Long clientId = 666L; //TODO: Add the ID of the logged-in client
        Offer offer = offerRepository.save(Offer.createOffer(createOfferRequestDTO, clientId));
        return OfferMapper.INSTANCE.map(offer);
    }

    @Override
    public OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
        Offer offer = findOfferById(id);
        offer.updateOffer(updateOfferRequestDTO);
        offerRepository.save(offer);
        return OfferMapper.INSTANCE.map(offer);
    }

    @Override
    public OfferIdResponseDTO archiveOffer(Long id) {
        Offer offer = findOfferById(id);
        if (offer.isActiveOffer()) {
            offer.archiveOffer();
            offerRepository.save(offer);
        }
        return OfferIdResponseDTO.builder().id(id).build();
    }

    @Override
    public List<OfferIdResponseDTO> archiveOffersByClientId(Long clientId) {
        List<Offer> offers = offerRepository.findOfferByClientId(clientId)
                .stream()
                .filter(Offer::isActiveOffer)
                .toList();

        if (offers.isEmpty()) {
            return List.of();
        }

        offers.forEach(Offer::archiveOffer);
        offerRepository.saveAll(offers);

        return offers.stream()
                .map(OfferMapper.INSTANCE::mapToOfferIdResponseDTO)
                .toList();
    }

    private Offer findOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
    }
}

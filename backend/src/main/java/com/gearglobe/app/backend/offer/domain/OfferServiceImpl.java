package com.gearglobe.app.backend.offer.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper.INSTANCE::offerToOfferDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OfferDTO> getOfferById(Long id) {
        return offerRepository.findById(id)
                .map(OfferMapper.INSTANCE::offerToOfferDTO);
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        Offer offer = OfferMapper.INSTANCE.offerDTOToOffer(offerDTO);
        Offer saveOffer = offerRepository.save(offer);
        return OfferMapper.INSTANCE.offerToOfferDTO(saveOffer);
    }

    @Override
    public OfferDTO updateOffer(OfferDTO offerDTO) throws ChangeSetPersister.NotFoundException {
        return offerRepository.findById(offerDTO.getId())
                .map(offer -> {
                    Offer newOffer = OfferMapper.INSTANCE.offerDTOToOffer(offerDTO);
                    newOffer.setId(offer.getId());
                    return offerRepository.save(newOffer);
                })
                .map(OfferMapper.INSTANCE::offerToOfferDTO)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public OfferDTO archiveOffer(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Offer> optionalOffer = offerRepository.findById(id);

        return optionalOffer.map(offer -> {
            offer.setStatus(OfferStatus.ARCHIVE);
            Offer archiveOffer = offerRepository.save(offer);
            return OfferMapper.INSTANCE.offerToOfferDTO(archiveOffer);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
}

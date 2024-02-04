package com.gearglobe.app.backend.offer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import com.gearglobe.app.backend.offer.api.dtos.OfferStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class OfferServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private OfferServiceImpl offerService;


    @Test
    void getAllOffers_ShouldReturnListOfOfferDTOs() {
        // GIVEN
        final List<Offer> expectedOffers = prepareOffers();

        // WHEN
        when(offerRepository.findAll()).thenReturn(expectedOffers);

        List<OfferDTO> result = offerService.getAllOffers();

        // THEN
        assertFalse(result.isEmpty());

        assertAll("Verify returned list of DTO offers",
                () -> assertEquals(expectedOffers.size(), result.size()),
                () -> assertThat(result).extracting(OfferDTO::getId).contains(1L, 2L, 3L),
                () -> assertThat(result).extracting(OfferDTO::getMark).contains("Mark1", "Mark2", "Mark3"),
                () -> assertThat(result).extracting(OfferDTO::getProductionYear).contains(2010L, 2011L, 2012L),
                () -> assertThat(result).extracting(OfferDTO::getMillage).contains(100000L, 100001L, 100002L),
                () -> assertThat(result).extracting(OfferDTO::getEngineCapacity).contains(2.0, 2.1, 2.2),
                () -> assertThat(result).extracting(OfferDTO::getDescription).contains("Description1", "Description2", "Description3"),
                () -> assertThat(result).extracting(OfferDTO::getPrice).contains(10000.0, 10001.0, 10002.0),
                () -> assertThat(result).extracting(OfferDTO::getCreateDate).isNotNull(),
                () -> assertThat(result).extracting(OfferDTO::getStatus).contains(OfferStatus.ACTIVE, OfferStatus.ACTIVE, OfferStatus.ACTIVE)
        );
    }

    @Test
    void getOfferById_ExistingId_ShouldReturnOfferDTO() {
        // GIVEN
        Long existingId = 1L;
        Offer expectedOffer = prepareOffers().getFirst();


        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(expectedOffer));

        Optional<OfferDTO> result = offerService.getOfferById(existingId);

        // THEN
        assertTrue(result.isPresent());

        assertAll("Verify returned DTO offer",
                () -> assertEquals(expectedOffer.getId(), result.get().getId()),
                () -> assertEquals(expectedOffer.getMark(), result.get().getMark()),
                () -> assertEquals(expectedOffer.getProductionYear(), result.get().getProductionYear()),
                () -> assertEquals(expectedOffer.getMillage(), result.get().getMillage()),
                () -> assertEquals(expectedOffer.getEngineCapacity(), result.get().getEngineCapacity()),
                () -> assertEquals(expectedOffer.getDescription(), result.get().getDescription()),
                () -> assertEquals(expectedOffer.getPrice(), result.get().getPrice()),
                () -> assertEquals(expectedOffer.getCreateDate(), result.get().getCreateDate()),
                () -> assertEquals(expectedOffer.getStatus(), result.get().getStatus())
        );
    }

    @Test
    void getOfferById_NonExistingId_ShouldThrowEntityNotFoundException() {
        // GIVEN
        Long nonExistingId = 999L;

        // WHEN
        when(offerRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(EntityNotFoundException.class, () -> offerService.getOfferById(nonExistingId));
    }

    @Test
    void createOffer_ShouldReturnOfferDTO() {
        // GIVEN
        OfferDTO offerDTO = OfferDTO.builder()
                .productionYear(2013L)
                .mark("Mark4")
                .millage(100003L)
                .engineCapacity(2.3)
                .description("Description4")
                .price(10003.0)
                .createDate(LocalDateTime.now())
                .status(OfferStatus.ACTIVE)
                .build();

        Offer expectedOffer = new Offer(4L, "Mark4", 2013L, 100003L, 2.3, "Description4", 10003.0, LocalDateTime.now(), OfferStatus.ACTIVE);


        // WHEN
        when(offerRepository.save(any(Offer.class))).thenReturn(expectedOffer);

        OfferDTO result = offerService.createOffer(offerDTO);

        // THEN
        assertAll("Verify returned DTO offer",
                () -> assertNotNull(result.getId()),
                () -> assertEquals(offerDTO.getMark(), result.getMark()),
                () -> assertEquals(offerDTO.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(offerDTO.getMillage(), result.getMillage()),
                () -> assertEquals(offerDTO.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(offerDTO.getDescription(), result.getDescription()),
                () -> assertEquals(offerDTO.getPrice(), result.getPrice()),
                () -> assertEquals(offerDTO.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(offerDTO.getStatus(), result.getStatus())
        );
    }

    @Test
    void updateOffer_ShouldReturnUpdatedOfferDTO() {
        // GIVEN
        Long existingId = 1L;
        Offer offer = prepareOffers().getFirst();
        OfferDTO updatedOfferDTO = OfferDTO.builder()
                .id(existingId)
                .productionYear(2013L)
                .mark("Mark4")
                .millage(100003L)
                .engineCapacity(2.3)
                .description("Description4")
                .price(10003.0)
                .createDate(LocalDateTime.now())
                .status(OfferStatus.ACTIVE)
                .build();

        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(OfferMapper.INSTANCE.offerDTOToOffer(updatedOfferDTO));

        OfferDTO result = offerService.updateOffer(updatedOfferDTO);

        // THEN
        assertAll("Verify returned updated DTO offer",
                () -> assertEquals(updatedOfferDTO.getId(), result.getId()),
                () -> assertEquals(updatedOfferDTO.getMark(), result.getMark()),
                () -> assertEquals(updatedOfferDTO.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(updatedOfferDTO.getMillage(), result.getMillage()),
                () -> assertEquals(updatedOfferDTO.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(updatedOfferDTO.getDescription(), result.getDescription()),
                () -> assertEquals(updatedOfferDTO.getPrice(), result.getPrice()),
                () -> assertEquals(updatedOfferDTO.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(updatedOfferDTO.getStatus(), result.getStatus())
        );
    }

    @Test
    void archiveOffer_ShouldReturnArchivedOfferDTO() {
        // GIVEN
        Long existingId = 1L;
        Offer offer = prepareOffers().getFirst();

        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenAnswer(
                invocation -> {
                    offer.setStatus(OfferStatus.ARCHIVE);
                    return offer;
                }
        );

        OfferDTO result = offerService.archiveOffer(existingId);

        // THEN
        assertAll("Verify returned DTO offer with changed status to ARCHIVE",
                () -> assertEquals(offer.getId(), result.getId()),
                () -> assertEquals(offer.getMark(), result.getMark()),
                () -> assertEquals(offer.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(offer.getMillage(), result.getMillage()),
                () -> assertEquals(offer.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(offer.getDescription(), result.getDescription()),
                () -> assertEquals(offer.getPrice(), result.getPrice()),
                () -> assertEquals(offer.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(result.getStatus(), OfferStatus.ARCHIVE)
        );
    }

    private static List<Offer> prepareOffers() {
        return List.of(
                new Offer(1L, "Mark1", 2010L, 100000L, 2.0, "Description1", 10000.0, LocalDateTime.now(), OfferStatus.ACTIVE),
                new Offer(2L, "Mark2", 2011L, 100001L, 2.1, "Description2", 10001.0, LocalDateTime.now(), OfferStatus.ACTIVE),
                new Offer(3L, "Mark3", 2012L, 100002L, 2.2, "Description3", 10002.0, LocalDateTime.now(), OfferStatus.ACTIVE)
        );
    }
}
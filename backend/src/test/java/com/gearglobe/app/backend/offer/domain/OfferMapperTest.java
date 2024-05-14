package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.OfferStatusDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OfferMapperTest {

    @Test
    void testOfferToOfferDTO() {
        // GIVEN
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setDescription("Sample Description");
        offer.setPrice(99.99);
        offer.setMark("Sample Mark");
        offer.setProductionYear(2020L);
        offer.setMillage(1000L);
        offer.setEngineCapacity(1.6);
        offer.setCreateDate(LocalDateTime.now());
        offer.setStatus(OfferStatusDTO.ARCHIVE);

        // WHEN
        OfferResponseDTO offerDTO = OfferMapper.INSTANCE.map(offer);

        // THEN
        assertAll("Verify mapping properties to OfferDTO",
                () -> assertEquals(offer.getId(), offerDTO.getId()),
                () -> assertEquals(offer.getDescription(), offerDTO.getDescription()),
                () -> assertEquals(offer.getPrice(), offerDTO.getPrice()),
                () -> assertEquals(offer.getMark(), offerDTO.getMark()),
                () -> assertEquals(offer.getProductionYear(), offerDTO.getProductionYear()),
                () -> assertEquals(offer.getMillage(), offerDTO.getMillage()),
                () -> assertEquals(offer.getEngineCapacity(), offerDTO.getEngineCapacity()),
                () -> assertEquals(offer.getCreateDate(), offerDTO.getCreateDate()),
                () -> assertEquals(offer.getStatus(), offerDTO.getStatus()));
    }

    @Test
    void testOfferDTOToOffer() {
        // GIVEN
        OfferResponseDTO offerDTO = OfferResponseDTO.builder()
                .description("Sample Description DTO")
                .price(88.88)
                .mark("Sample Mark DTO")
                .productionYear(2021L)
                .millage(2000L)
                .engineCapacity(2.0)
                .createDate(LocalDateTime.now())
                .status(OfferStatusDTO.ACTIVE)
                .build();

        // WHEN
        Offer offer = OfferMapper.INSTANCE.map(offerDTO);

        // THEN
        assertAll("Verify mapping properties to Offer",
                () -> assertEquals(offerDTO.getDescription(), offer.getDescription()),
                () -> assertEquals(offerDTO.getPrice(), offer.getPrice()),
                () -> assertEquals(offerDTO.getMark(), offer.getMark()),
                () -> assertEquals(offerDTO.getProductionYear(), offer.getProductionYear()),
                () -> assertEquals(offerDTO.getMillage(), offer.getMillage()),
                () -> assertEquals(offerDTO.getEngineCapacity(), offer.getEngineCapacity()));
    }
}
package com.gearglobe.app.backend.offer.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import com.gearglobe.app.backend.offer.api.dtos.OfferStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:/data/insert_offers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OfferControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllOffers() throws Exception {
        String response = mockMvc.perform(get(OfferController.OFFER_URL))
                .andReturn().getResponse().getContentAsString();
        List<OfferDTO> offers = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(3, offers.size());
    }

    @Test
    void shouldReturnOfferById() throws Exception {
        String response = mockMvc.perform(get(OfferController.OFFER_URL + "/1"))
                .andReturn().getResponse().getContentAsString();

        OfferDTO offer = objectMapper.readValue(response, OfferDTO.class);

        assertEquals(1, offer.getId());
    }

    @Test
    @Transactional
    void shouldCreateOffer() throws Exception {
        OfferDTO testeeOffer = OfferDTO.builder()
                .mark("Mark4")
                .productionYear(2010L)
                .millage(100000L)
                .engineCapacity(2.0)
                .description("Description4")
                .price(10000.0)
                .createDate(LocalDateTime.now())
                .status(OfferStatus.ACTIVE)
                .build();

        String response = mockMvc.perform(post(OfferController.OFFER_URL)
                        .param("clientId", "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testeeOffer)))
                .andReturn().getResponse().getContentAsString();

        OfferDTO offer = objectMapper.readValue(response, OfferDTO.class);

        assertEquals(4, offer.getId());
    }

    @Test
    @Transactional
    void shouldUpdateOffer() throws Exception {
        OfferDTO testeeOffer = OfferDTO.builder()
                .id(1L)
                .mark("MarkUpdated")
                .productionYear(2020L)
                .millage(50000L)
                .engineCapacity(2.0)
                .description("Description1")
                .price(20000.0)
                .createDate(LocalDateTime.parse("2022-01-18T12:00:00"))
                .status(OfferStatus.ACTIVE)
                .build();

        String response = mockMvc.perform(put(OfferController.OFFER_URL + "/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testeeOffer)))
                .andReturn().getResponse().getContentAsString();

        OfferDTO offer = objectMapper.readValue(response, OfferDTO.class);

        assertAll("Should return updated offer values",
                () -> assertEquals(1, offer.getId()),
                () -> assertEquals("MarkUpdated", offer.getMark()),
                () -> assertEquals("Description1", offer.getDescription())
        );
    }

    @Test
    @Transactional
    void shouldArchiveOffer() throws Exception {
        String response = mockMvc.perform(delete(OfferController.OFFER_URL + "/1"))
                .andReturn().getResponse().getContentAsString();

        OfferDTO offer = objectMapper.readValue(response, OfferDTO.class);

        assertAll("Should return archived offer values",
                () -> assertEquals(1, offer.getId()),
                () -> assertEquals(OfferStatus.ARCHIVE, offer.getStatus())
        );
    }
}
package com.gearglobe.app.backend.offer.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearglobe.dto.*;
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
        List<OfferResponseDTO> offers = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(4, offers.size());
    }

    @Test
    void shouldReturnOfferById() throws Exception {
        String response = mockMvc.perform(get(OfferController.OFFER_URL + "/1"))
                .andReturn().getResponse().getContentAsString();

        OfferResponseDTO offer = objectMapper.readValue(response, OfferResponseDTO.class);

        assertEquals(1, offer.getId());
    }

    @Test
    @Transactional
    void shouldCreateOffer() throws Exception {
        CreateOfferRequestDTO testeeOffer = prepareCreateOfferRequestDTO();

        String response = mockMvc.perform(post(OfferController.OFFER_URL)
                        .param("clientId", "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testeeOffer)))
                .andReturn().getResponse().getContentAsString();

        OfferResponseDTO offer = objectMapper.readValue(response, OfferResponseDTO.class);

        assertEquals(5, offer.getId());
    }

    @Test
    @Transactional
    void shouldUpdateOffer() throws Exception {
        UpdateOfferRequestDTO testeeOffer = prepareUpdateOfferRequestDTO();

        String response = mockMvc.perform(put(OfferController.OFFER_URL + "/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testeeOffer)))
                .andReturn().getResponse().getContentAsString();

        OfferResponseDTO offer = objectMapper.readValue(response, OfferResponseDTO.class);

        assertAll("Should return updated offer values",
                () -> assertEquals(1, offer.getId()),
                () -> assertEquals("MarkUpdated", offer.getMark()),
                () -> assertEquals("Description1", offer.getDescription())
        );
    }

    @Test
    @Transactional
    void shouldArchiveOffer() throws Exception {
        String response = mockMvc.perform(delete(OfferController.OFFER_URL + "/1")
                        .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        OfferIdResponseDTO offer = objectMapper.readValue(response, OfferIdResponseDTO.class);

        assertAll("Should return archived offer values",
                () -> assertEquals(1, offer.getId())
        );
    }

    @Test
    void shouldArchiveClientOffers() throws Exception {
        String response = mockMvc.perform(delete(OfferController.OFFER_URL + "/2/archiveAll")
                        .contentType("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<OfferIdResponseDTO> offers = objectMapper.readValue(response, new TypeReference<>(){});

        assertAll("Should return client's archived offer ids",
                () -> assertEquals(2, offers.size())
        );
    }

    private CreateOfferRequestDTO prepareCreateOfferRequestDTO() {
        return CreateOfferRequestDTO.builder()
                .mark("Mark4")
                .productionYear(2010L)
                .millage(100000L)
                .engineCapacity(2.0)
                .description("Description4")
                .price(10000.0)
                .build();
    }

    private UpdateOfferRequestDTO prepareUpdateOfferRequestDTO() {
        return UpdateOfferRequestDTO.builder()
                .mark("MarkUpdated")
                .productionYear(2020L)
                .millage(50000L)
                .engineCapacity(2.0)
                .description("Description1")
                .price(20000.0)
                .build();
    }
}
package com.gearglobe.app.backend.offer.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OfferControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/data/insert_offers.sql")
    void shouldReturnAllOffers() throws Exception {
        String response = mockMvc.perform(get(OfferController.OFFER_URL))
                .andReturn().getResponse().getContentAsString();
        List<OfferDTO> offers = objectMapper.readValue(response, new TypeReference<>() {});
        assertEquals(3, offers.size());
    }
}
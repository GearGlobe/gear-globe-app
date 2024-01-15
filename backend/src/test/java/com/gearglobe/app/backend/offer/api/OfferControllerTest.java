package com.gearglobe.app.backend.offer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import com.gearglobe.app.backend.offer.domain.OfferFacade;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferFacade offerFacade;

    @InjectMocks
    private OfferController offerController;

    @Autowired
    ObjectMapper jsonMapper = new ObjectMapper();

    private static final String OFFER_URL = OfferController.OFFER_URL;

    @Test
    public void testGetAllOffers() throws Exception {
        List<OfferDTO> testeeOffers = List.of(
                OfferDTO.builder().build(),
                OfferDTO.builder().build()
        );

        when(offerFacade.getAllOffers()).thenReturn(testeeOffers);

        String jsonOffers = jsonMapper.writeValueAsString(testeeOffers);

        mockMvc.perform(get(OFFER_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOffers));
    }

    @Test
    public void testGetOfferById() throws Exception {
        Long offerId = 1L;

        OfferDTO testeeOffer = OfferDTO.builder()
                .id(offerId)
                .build();

        when(offerFacade.getOfferById(offerId)).thenReturn(Optional.ofNullable(testeeOffer));

        mockMvc.perform(get(OFFER_URL + "/{id}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(testeeOffer)));
    }

    @Test
    public void testCreateOffer() throws Exception {
        OfferDTO testeeOffer = OfferDTO.builder()
                .build();

        when(offerFacade.createOffer(any(OfferDTO.class))).thenReturn(testeeOffer);

        String jsonOffer = jsonMapper.writeValueAsString(testeeOffer);

        mockMvc.perform(post(OFFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffer))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOffer))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testUpdateOffer() throws Exception {
        Long offerId = 1L;

        OfferDTO testeeOffer = OfferDTO.builder()
                .id(offerId)
                .build();

        when(offerFacade.updateOffer(any(OfferDTO.class))).thenReturn(testeeOffer);

        String jsonOffer = jsonMapper.writeValueAsString(testeeOffer);

        mockMvc.perform(put(OFFER_URL + "/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffer))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOffer))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testArchiveOffer() throws Exception {
        Long offerId = 1L;

        OfferDTO testeeOffer = OfferDTO.builder()
                .id(offerId)
                .build();

        when(offerFacade.archiveOffer(offerId)).thenReturn(testeeOffer);

        String jsonOffer = jsonMapper.writeValueAsString(testeeOffer);

        mockMvc.perform(delete(OFFER_URL + "/{id}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonOffer))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetOfferByIdNullResult() throws Exception {
        Long offerId = 1L;
        when(offerFacade.getOfferById(offerId)).thenReturn(null);

        mockMvc.perform(get(OFFER_URL + "/{id}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testGetAllOffersEmptyList() throws Exception {
        when(offerFacade.getAllOffers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(OFFER_URL))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateOfferInvalidInput() throws Exception {
        String invalidJson = "invalidJson";

        mockMvc.perform(post(OFFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateOfferMissingId() throws Exception {
        String jsonOffer = jsonMapper.writeValueAsString(OfferDTO.builder().build());

        mockMvc.perform(put(OFFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffer))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteNonexistentOffer() throws Exception {
        Long nonExistentId = 999L;

        when(offerFacade.archiveOffer(nonExistentId)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete(OFFER_URL + "/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

}

package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateBaseOfferRequestDTO;
import com.gearglobe.dto.OfferStatusDTO;
import com.gearglobe.dto.OfferTypeDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "offer")
class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "offer_type")
    @Enumerated(EnumType.STRING)
    private OfferTypeDTO offerTypeDTO;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean negotiable;

    @CreatedDate
    @Column(nullable = false, name = "create_date")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatusDTO status;

    @Column(nullable = false, name = "client_id")
    private Long clientId;

    @OneToOne(mappedBy = "offer")
    private CarOffer carOffer;

    public static Offer createOffer(CreateBaseOfferRequestDTO createBaseOfferRequestDTO, Long clientId) {
        return Offer.builder()
                .offerTypeDTO(createBaseOfferRequestDTO.getOfferType())
                .title(createBaseOfferRequestDTO.getTitle())
                .description(createBaseOfferRequestDTO.getDescription())
                .price(createBaseOfferRequestDTO.getPrice())
                .negotiable(createBaseOfferRequestDTO.getNegotiable())
                .status(OfferStatusDTO.ACTIVE)
                .clientId(clientId)
                .build();
    }

    public void updateOffer(UpdateOfferRequestDTO updateOfferRequestDTO) {
        this.description = updateOfferRequestDTO.getDescription();
        this.price = updateOfferRequestDTO.getPrice();
    }

    public boolean isActiveOffer() {
        return this.status == OfferStatusDTO.ACTIVE;
    }

    public void archiveOffer() {
        this.status = OfferStatusDTO.ARCHIVE;
    }
}

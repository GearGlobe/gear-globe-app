package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferStatusDTO;
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

    @Column(nullable = false)
    private String mark;

    @Column(nullable = false, name = "production_year")
    private Long productionYear;

    @Column(nullable = false)
    private Long millage;

    @Column(nullable = false, name = "engine_capacity")
    private Double engineCapacity;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatusDTO status;

    @Column(nullable = false, name = "client_id")
    private Long clientId;

    public static Offer createOffer(CreateOfferRequestDTO createOfferRequestDTO, Long clientId) {
        Offer offer = new Offer();
        offer.mark = createOfferRequestDTO.getMark();
        offer.productionYear = createOfferRequestDTO.getProductionYear();
        offer.millage = createOfferRequestDTO.getMillage();
        offer.engineCapacity = createOfferRequestDTO.getEngineCapacity();
        offer.description = createOfferRequestDTO.getDescription();
        offer.price = createOfferRequestDTO.getPrice();
        offer.status = OfferStatusDTO.ACTIVE;
        offer.clientId = clientId;
        return offer;
    }

    public void updateOffer(UpdateOfferRequestDTO updateOfferRequestDTO) {
        this.mark = updateOfferRequestDTO.getMark();
        this.productionYear= updateOfferRequestDTO.getProductionYear();
        this.millage = updateOfferRequestDTO.getMillage();
        this.engineCapacity = updateOfferRequestDTO.getEngineCapacity();
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

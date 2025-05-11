package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "offer")
abstract class Offer {
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

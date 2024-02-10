package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "address")
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String houseNumber;

    @NonNull
    private String country;

    @OneToOne(mappedBy = "address")
    private Client client;

    @NonNull
    private AddressStatus status;
}

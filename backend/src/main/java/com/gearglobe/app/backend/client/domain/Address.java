package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.CreateAddressRequestDTO;
import com.gearglobe.dto.UpdateAddressRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "address")
@Builder
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false, name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    private Client client;

    public static Address createAddress(CreateAddressRequestDTO createAddressRequestDTO) {
        return Address.builder()
                .city(createAddressRequestDTO.getCity())
                .street(createAddressRequestDTO.getStreet())
                .houseNumber(createAddressRequestDTO.getHouseNumber())
                .apartmentNumber(createAddressRequestDTO.getApartmentNumber())
                .country(createAddressRequestDTO.getCountry())
                .build();
    }

    public void updateAddress(UpdateAddressRequestDTO updateAddressRequestDTO) {
        this.city = updateAddressRequestDTO.getCity();
        this.street = updateAddressRequestDTO.getStreet();
        this.houseNumber = updateAddressRequestDTO.getHouseNumber();
        this.apartmentNumber = updateAddressRequestDTO.getApartmentNumber();
        this.country = updateAddressRequestDTO.getCountry();
    }
}

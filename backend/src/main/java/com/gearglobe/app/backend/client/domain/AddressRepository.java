package com.gearglobe.app.backend.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByClientId(Long clientId);

}

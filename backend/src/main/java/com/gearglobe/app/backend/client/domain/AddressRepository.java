package com.gearglobe.app.backend.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddressRepository extends JpaRepository<Address, Long> {
}

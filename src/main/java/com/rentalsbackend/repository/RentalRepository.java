package com.rentalsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rentalsbackend.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}

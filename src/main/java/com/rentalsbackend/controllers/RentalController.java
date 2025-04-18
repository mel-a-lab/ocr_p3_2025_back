package com.rentalsbackend.controllers;

import com.rentalsbackend.entity.Rental;
import com.rentalsbackend.repository.RentalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalRepository rentalRepository;

    public RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // GET /api/rentals
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        return ResponseEntity.ok(rentalRepository.findAll());
    }

    // GET /api/rentals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        return rentalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/rentals
    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody Rental rental) {
        rental.setCreatedAt(Instant.now());
        rental.setUpdatedAt(Instant.now());
        rentalRepository.save(rental);

        return ResponseEntity.ok().body(
                Map.of("message", "Rental created !")
        );
    }

    // PUT /api/rentals/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental updatedRental) {
        return rentalRepository.findById(id).map(rental -> {
            rental.setName(updatedRental.getName());
            rental.setSurface(updatedRental.getSurface());
            rental.setPrice(updatedRental.getPrice());
            rental.setPicture(updatedRental.getPicture());
            rental.setDescription(updatedRental.getDescription());
            rental.setUpdatedAt(Instant.now());
            return ResponseEntity.ok(rentalRepository.save(rental));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/rentals/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Integer id) {
        return rentalRepository.findById(id).map(rental -> {
            rentalRepository.delete(rental);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


}

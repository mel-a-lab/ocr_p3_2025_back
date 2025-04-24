package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.RentalRequest;
import com.rentalsbackend.dto.RentalResponse;
import com.rentalsbackend.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable Integer id) {
        return rentalService.findById(id)
                .map(rental -> ResponseEntity.ok((Object) rental))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Rental not found")));
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody @Valid RentalRequest rentalRequest) {
        RentalResponse created = rentalService.create(rentalRequest);
        return ResponseEntity.ok(Map.of("message", "Rental created !", "rental", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(@PathVariable Integer id, @RequestBody @Valid RentalRequest rentalRequest) {
        return rentalService.update(id, rentalRequest)
                .map(updated -> ResponseEntity.ok(Map.of("message", "Rental updated !", "rental", updated)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Rental not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Integer id) {
        return rentalService.delete(id)
                ? ResponseEntity.ok(Map.of("message", "Rental deleted"))
                : ResponseEntity.status(404).body(Map.of("message", "Rental not found"));
    }
}

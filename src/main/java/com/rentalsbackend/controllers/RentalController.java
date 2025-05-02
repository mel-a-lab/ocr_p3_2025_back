package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.RentalRequest;
import com.rentalsbackend.dto.RentalResponse;
import com.rentalsbackend.errors.exceptions.ResourceNotFoundException;
import com.rentalsbackend.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer id) {
        return rentalService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with ID " + id + " not found"));
    }
    // pareil ici retirer la logique pour getRentalById

    @PostMapping
    public ResponseEntity<RentalResponse> createRental(@RequestBody @Valid RentalRequest rentalRequest) {
        RentalResponse created = rentalService.create(rentalRequest);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable Integer id, @RequestBody @Valid RentalRequest rentalRequest) {
        return rentalService.update(id, rentalRequest)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with ID " + id + " not found"));
    }

    // retirer la logique dans updateRental (retourner ResponseEntityNoContent), mettre
    // en paramètre l'objet qui est mis à jour

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.delete(id);
        return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
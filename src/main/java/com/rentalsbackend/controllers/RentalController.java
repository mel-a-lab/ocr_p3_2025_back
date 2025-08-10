package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.RentalRequest;
import com.rentalsbackend.dto.RentalResponse;
import com.rentalsbackend.dto.RentalsResponse;
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
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(new RentalsResponse(rentalService.findAll()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer id) {
        RentalResponse rental = rentalService.findByIdOrFail(id);
        return ResponseEntity.ok(rental);
    }

    // pareil ici retirer la logique pour getRentalById

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<RentalResponse> createRental(@ModelAttribute @Valid RentalRequest rentalRequest) {
        RentalResponse created = rentalService.create(rentalRequest);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable Integer id,
            @ModelAttribute @Valid RentalRequest rentalRequest) {
        rentalService.updateOrFail(id, rentalRequest); // s’il échoue, il lance une exception
        return ResponseEntity.noContent().build();
    }

    // retirer la logique dans updateRental (retourner ResponseEntityNoContent),
    // mettre
    // en paramètre l'objet qui est mis à jour
    // => fait

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteOrFail(id);
        return ResponseEntity.noContent().build();
    }

    // => fait

}
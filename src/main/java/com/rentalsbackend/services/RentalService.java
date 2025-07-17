package com.rentalsbackend.services;

import com.rentalsbackend.dto.RentalRequest;
import com.rentalsbackend.dto.RentalResponse;
import com.rentalsbackend.entity.Rental;
import com.rentalsbackend.errors.exceptions.ResourceNotFoundException;
import com.rentalsbackend.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CloudinaryPictureService cloudinaryPictureService;

    public RentalService(RentalRepository rentalRepository, CloudinaryPictureService cloudinaryPictureService) {
        this.rentalRepository = rentalRepository;
        this.cloudinaryPictureService = cloudinaryPictureService;
    }

    public List<RentalResponse> findAll() {
        return rentalRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Optional<RentalResponse> findById(Integer id) {
        return rentalRepository.findById(id)
                .map(this::mapToResponse);
    }

    public RentalResponse create(RentalRequest request) {
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setCreatedAt(Instant.now());
        rental.setUpdatedAt(Instant.now());

        MultipartFile file = request.getPicture();
        String imageUrl = cloudinaryPictureService.uploadFile(file, "rentals");

        rental.setPicture(imageUrl);

        return mapToResponse(rentalRepository.save(rental));
    }

    public Optional<RentalResponse> update(Integer id, RentalRequest request) {
        return rentalRepository.findById(id).map(rental -> {
            rental.setName(request.getName());
            rental.setSurface(request.getSurface());
            rental.setPrice(request.getPrice());

            MultipartFile file = request.getPicture();
            String imageUrl = cloudinaryPictureService.uploadFile(file, "rentals");

            rental.setPicture(imageUrl);

            rental.setDescription(request.getDescription());
            rental.setUpdatedAt(Instant.now());
            return mapToResponse(rentalRepository.save(rental));
        });
    }

    public boolean delete(Integer id) {
        return rentalRepository.findById(id).map(rental -> {
            rentalRepository.delete(rental);
            return true;
        }).orElse(false);
    }

    private RentalResponse mapToResponse(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getCreatedAt(),
                rental.getUpdatedAt());
    }

    public void updateOrFail(Integer id, RentalRequest request) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with ID " + id + " not found"));

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        MultipartFile file = request.getPicture();
        String imageUrl = cloudinaryPictureService.uploadFile(file, "rentals");

        rental.setPicture(imageUrl);
        rental.setDescription(request.getDescription());
        rental.setUpdatedAt(Instant.now());

        rentalRepository.save(rental);
    }

    public RentalResponse findByIdOrFail(Integer id) {
        return rentalRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with ID " + id + " not found"));
    }

    public void deleteOrFail(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with ID " + id + " not found"));
        rentalRepository.delete(rental);
    }
    

}

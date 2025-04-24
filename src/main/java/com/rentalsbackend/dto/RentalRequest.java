package com.rentalsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class RentalRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "La surface est obligatoire")
    private BigDecimal surface;

    @NotNull(message = "Le prix est obligatoire")
    private BigDecimal price;

    @NotBlank(message = "L'URL de l'image est obligatoire")
    private String picture;

    @NotBlank(message = "La description est obligatoire")
    private String description;
}

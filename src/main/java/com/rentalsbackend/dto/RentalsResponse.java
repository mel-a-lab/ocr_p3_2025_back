package com.rentalsbackend.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class RentalsResponse {

    private List<RentalResponse> rentals;

}

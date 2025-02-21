package com.rentalsbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String token;


    public RegisterResponse(String token) {
        this.token = token;
    }
}

package com.rentalsbackend.controllers;


import com.rentalsbackend.dto.LoginRequest;
import com.rentalsbackend.dto.RegisterRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.rentalsbackend.services.JWTService;


@RestController
public class LoginController {


    private JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String getToken(@RequestBody LoginRequest loginRequest, Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }

    @PostMapping("/register")
    public RegisterRequest register(@RequestBody RegisterRequest registerRequest) {
        return registerRequest;
    }

}

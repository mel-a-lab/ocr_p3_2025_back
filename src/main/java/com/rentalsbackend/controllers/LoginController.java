package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.RegisterRequest;
import com.rentalsbackend.dto.RegisterResponse;
import com.rentalsbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

        if (registerRequest.getName() == null || registerRequest.getEmail() == null || registerRequest.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }


        RegisterResponse response = userService.registerUser(registerRequest);

        if (response.getToken() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

}

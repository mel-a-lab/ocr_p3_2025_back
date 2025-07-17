package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.LoginRequest;
import com.rentalsbackend.dto.LoginResponse;
import com.rentalsbackend.dto.RegisterRequest;
import com.rentalsbackend.dto.RegisterResponse;
import com.rentalsbackend.dto.UserResponse;
import com.rentalsbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponse> currentUserName(Authentication authentication) {

        String username = authentication.getName();
        UserResponse response = userService.findUserByName(username);
        return ResponseEntity.ok(response);
    }
    
}

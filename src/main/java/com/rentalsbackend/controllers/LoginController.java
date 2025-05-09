package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.LoginRequest;
import com.rentalsbackend.dto.LoginResponse;
import com.rentalsbackend.dto.RegisterRequest;
import com.rentalsbackend.dto.RegisterResponse;
import com.rentalsbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/auth")
// public class LoginController {

//     private final UserService userService;

//     public LoginController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

//         if (registerRequest.getName() == null || registerRequest.getEmail() == null
//                 || registerRequest.getPassword() == null) {
//             return ResponseEntity.badRequest().build();
//         }

//         RegisterResponse response = userService.registerUser(registerRequest);

//         if (response.getToken() == null) {
//             return ResponseEntity.badRequest().build();
//         }

//         return ResponseEntity.ok(response);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//         if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
//             return ResponseEntity.badRequest().build();
//         }

//         LoginResponse response = userService.loginUser(loginRequest);

//         if (response.getToken() == null) {
//             return ResponseEntity.status(401).build();
//         }

//         return ResponseEntity.ok(response);
//     }

// }

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
}

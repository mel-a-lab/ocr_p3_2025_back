package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @PostMapping
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageRequest messageRequest,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        if (messageRequest.getContent() == null || messageRequest.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Content cannot be empty");
        }

        return ResponseEntity.ok("Message send with success");
    }
}

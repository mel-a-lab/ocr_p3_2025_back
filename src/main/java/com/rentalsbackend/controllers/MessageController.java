package com.rentalsbackend.controllers;

import com.rentalsbackend.dto.MessageRequest;
import com.rentalsbackend.entity.Message;
import com.rentalsbackend.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestBody MessageRequest messageRequest,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
        }

        if (messageRequest.getContent() == null || messageRequest.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Content cannot be empty"));
        }

        // Sauvegarde du message
        Message message = new Message();
        message.setMessage(messageRequest.getContent());
        message.setCreatedAt(Instant.now());
        message.setUpdatedAt(Instant.now());

        messageRepository.save(message);

        return ResponseEntity.ok(Map.of("message", "Message send with success"));
    }
}

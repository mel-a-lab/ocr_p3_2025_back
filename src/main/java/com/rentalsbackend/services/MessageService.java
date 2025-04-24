package com.rentalsbackend.services;

import com.rentalsbackend.dto.MessageRequest;
import com.rentalsbackend.dto.MessageResponse;
import com.rentalsbackend.entity.Message;
import com.rentalsbackend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageResponse sendMessage(MessageRequest request) {
        Message message = new Message();
        message.setMessage(request.getContent());
        message.setCreatedAt(Instant.now());
        message.setUpdatedAt(Instant.now());

        Message saved = messageRepository.save(message);

        return new MessageResponse(
                saved.getId(),
                saved.getMessage(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}

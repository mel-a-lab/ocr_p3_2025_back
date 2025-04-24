package com.rentalsbackend.dto;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class MessageResponse {
    private Integer id;
    private String message;
    private Instant createdAt;
    private Instant updatedAt;
}

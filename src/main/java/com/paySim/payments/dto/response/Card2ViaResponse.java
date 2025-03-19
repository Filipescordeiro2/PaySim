package com.paySim.payments.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Card2ViaResponse(String message,
                               LocalDateTime createdAt) {
}

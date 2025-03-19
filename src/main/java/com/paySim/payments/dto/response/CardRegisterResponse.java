package com.paySim.payments.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CardRegisterResponse(String message,
                                   LocalDateTime createdAt) {
}

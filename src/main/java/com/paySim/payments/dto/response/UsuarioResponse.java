package com.paySim.payments.dto.response;

import com.paySim.payments.enums.StatusUsuario;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioResponse (String cpf,
                               String nameUsuario,
                               String email,
                               String phone,
                               int score,
                               StatusUsuario statusUsuario,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
}

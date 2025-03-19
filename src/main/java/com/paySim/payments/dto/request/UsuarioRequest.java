package com.paySim.payments.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotNull(message = "cpf is required")
    @CPF(message = "cpf is invalid")
    private String cpf;

    @NotNull(message = "name of user is required")
    private String nameUsuario;

    @NotNull(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotNull(message = "phone is required")
    private String phone;
}
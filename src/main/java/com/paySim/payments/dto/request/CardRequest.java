package com.paySim.payments.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    @CPF(message = "O campo cpfUsuario deve ser um CPF válido")
    @NotNull(message = "O campo cpfUsuario é obrigatório")
    private String cpfUsuario;

    @NotNull(message = "O campo cardHolderName é obrigatório")
    private String cardHolderName;

    @NotNull(message = "O campo password é obrigatório")
    private String password;
}

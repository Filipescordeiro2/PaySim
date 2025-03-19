package com.paySim.payments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card2viaRequest {

    private String cardHolderName;
    private String password;
    private String cpfUsuario;
    private UUID cardId;
}

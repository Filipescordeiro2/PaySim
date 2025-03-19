package com.paySim.payments.utils;

import com.paySim.payments.domain.Card;
import com.paySim.payments.domain.Usuario;
import com.paySim.payments.dto.request.Card2viaRequest;
import com.paySim.payments.dto.request.CardRequest;
import com.paySim.payments.enums.StatusCard;
import com.paySim.payments.enums.TypeCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardUtils {

    private static final Set<String> generatedNumbers = new HashSet<>();
    private static final Random random = new Random();
    private final CardValidation validation;

    public Card generateCard(CardRequest request,
                             TypeCard cardType,
                             Usuario usuario) {

        String cardNumber = generateUniqueCardNumber();
        String expiryDate = generateExpiryDate();
        String cvv = generateCvv();
        validation.validScore(usuario.getScore());
        validation.validPassword(request.getPassword());
        validation.validExistCardActiveForUsuario(usuario.getCpf());
        var limite = generateCreditLimit(usuario.getScore());
        return Card.builder()
                .cardNumber(cardNumber)
                .cardHolderName((request.getCardHolderName().toUpperCase()))
                .expiryDate(expiryDate)
                .cvv(cvv)
                .password(request.getPassword())
                .cardType(cardType)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .statusCard(StatusCard.ACTIVE)
                .usuario(usuario)
                .limitCard(limite)
                .limitAprove(limite)
                .build();
    }

    public Card generateCard2Via(Card2viaRequest request,
                                 Double limitCard,
                                 TypeCard cardType,
                                 Usuario usuario) {

        String cardNumber = generateUniqueCardNumber();
        String expiryDate = generateExpiryDate();
        String cvv = generateCvv();

        validation.validPassword(request.getPassword());
        validation.validExistCardActiveForUsuario(usuario.getCpf());

        return Card.builder()
                .cardNumber(cardNumber)
                .cardHolderName((request.getCardHolderName().toUpperCase()))
                .expiryDate(expiryDate)
                .cvv(cvv)
                .password(request.getPassword())
                .cardType(cardType)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .statusCard(StatusCard.ACTIVE)
                .usuario(usuario)
                .limitCard(limitCard)
                .limitAprove(limitCard)
                .build();
    }

    private double generateCreditLimit(int score) {
        Random random = new Random();
        if (score < 140) {
            return 0.0; // Score less than 140, limit is 0
        } else if (score >= 700) {
            return Math.round(7000.0 + (3000.0 * random.nextDouble())); // 7000 to 10000
        } else if (score >= 600) {
            return Math.round(3000.0 + (2000.0 * random.nextDouble())); // 3000 to 5000
        } else if (score >= 500) {
            return Math.round(1000.0 + (1000.0 * random.nextDouble())); // 1000 to 2000
        } else if (score >= 400) {
            return Math.round(500.0 + (500.0 * random.nextDouble())); // 500 to 1000
        } else if (score >= 300) {
            return Math.round(200.0 + (300.0 * random.nextDouble())); // 200 to 500
        } else {
            return Math.round(100.0 + (100.0 * random.nextDouble())); // 100 to 200
        }
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            StringBuilder cardNumberBuilder = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumberBuilder.append(random.nextInt(10));
            }
            cardNumber = cardNumberBuilder.toString();
        } while (generatedNumbers.contains(cardNumber));
        generatedNumbers.add(cardNumber);
        return cardNumber;
    }

    private String generateExpiryDate() {
        LocalDateTime expiry = LocalDateTime.now().plusYears(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiry.format(formatter);
    }

    private String generateCvv() {
        StringBuilder cvvBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvvBuilder.append(random.nextInt(10));
        }
        return cvvBuilder.toString();
    }

    public String maskCardNumber(String cardNumber) {
        return "**** **** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public String maskCvv(String cvv) {
        return cvv.charAt(0) + "**";
    }
}
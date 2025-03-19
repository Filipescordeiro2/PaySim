package com.paySim.payments.utils;

import com.paySim.payments.dto.request.CardRequest;
import com.paySim.payments.enums.StatusCard;
import com.paySim.payments.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardValidation {

    private final CardRepository cardRepository;

    public void validExistCardActiveForUsuario(String cpfUsuario) {
        var card = cardRepository
                .findByUsuarioCpfAndStatusCard(cpfUsuario, StatusCard.ACTIVE);
        if (card.isPresent()){
            throw new RuntimeException("User already has an active card");
        }
    }

    public void validPassword(String password) {
        if (password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        } else if (password.length() > 12) {
            throw new RuntimeException("Password must be at most 12 characters");
        } else if (password.contains(" ")) {
            throw new RuntimeException("Password must not contain spaces");
        } else if (!password.matches("\\d+")) {
            throw new RuntimeException("Password must be numeric");
        }

        String[] forbiddenSequences = {"password", "123456", "654321", "qwerty"};
        for (String sequence : forbiddenSequences) {
            if (password.contains(sequence)) {
                throw new RuntimeException("Password must not contain the sequence " + sequence);
            }
        }
    }

    public void validScore(int score){
        if (score < 140){
            throw new RuntimeException("Card rejected");
        }
    }

    public void validNewLimitCard(Double newLimitCard,Double limitCard){

        if (newLimitCard < 0) {
            throw new RuntimeException("The new limit card must be greater than zero");
        }else if(newLimitCard > limitCard){
            throw new RuntimeException("The new card limit cannot be greater than the released limit");
        }
    }
}

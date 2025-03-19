package com.paySim.payments.service;

import com.paySim.payments.dto.request.Card2viaRequest;
import com.paySim.payments.dto.request.CardRequest;
import com.paySim.payments.dto.response.Card2ViaResponse;
import com.paySim.payments.dto.response.CardRegisterResponse;
import com.paySim.payments.dto.response.CardNewLimiteCardResponse;
import com.paySim.payments.enums.StatusCard;
import com.paySim.payments.enums.TypeCard;
import com.paySim.payments.repository.CardRepository;
import com.paySim.payments.utils.CardUtils;
import com.paySim.payments.utils.CardValidation;
import com.paySim.payments.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardUtils cardUtils;
    private final CardValidation cardValidation;
    private final UsuarioUtils usuarioUtils;

    @Transactional
    public CardRegisterResponse createCardCredit(CardRequest request) {
        try {
            var usuario = usuarioUtils.findById(request.getCpfUsuario());
            var card = cardUtils.generateCard(request, TypeCard.CREDIT, usuario);
            cardRepository.save(card);
            return CardRegisterResponse
                    .builder()
                    .message("Card created successfully")
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error creating card: " + e.getMessage());
        }
    }

    @Transactional
    public Card2ViaResponse emitir2via(Card2viaRequest request) {
        var cards = cardRepository.findById(request.getCardId());

        cards.stream().forEach(card -> {
            card.setStatusCard(StatusCard.INACTIVE);
            cardRepository.save(card);
            var usuario = usuarioUtils.findById(request.getCpfUsuario());
            var card2via = cardUtils.generateCard2Via(request, card.getLimitCard(),
                                                      card.getCardType(), usuario);
            cardRepository.save(card2via);
        });
        return Card2ViaResponse
                .builder()
                .message("Card successfully 2via")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public CardNewLimiteCardResponse uptadeLimitCard(UUID cardId, Double newLimitCard) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(()-> new RuntimeException("Card not found"));

        cardValidation.validNewLimitCard(newLimitCard, card.getLimitAprove());
        card.setLimitCard(newLimitCard);
        cardRepository.save(card);

        return CardNewLimiteCardResponse
                .builder()
                .message("Limit card updated successfully")
                .limitCard(newLimitCard)
                .createdAt(LocalDateTime.now())
                .build();
    }

}

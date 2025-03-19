package com.paySim.payments.controller;

import com.paySim.payments.dto.request.Card2viaRequest;
import com.paySim.payments.dto.request.CardRequest;
import com.paySim.payments.dto.response.Card2ViaResponse;
import com.paySim.payments.dto.response.CardNewLimiteCardResponse;
import com.paySim.payments.dto.response.CardRegisterResponse;
import com.paySim.payments.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardRegisterResponse createCard(@RequestBody CardRequest request){
        return cardService.createCardCredit(request);
    }

    @PostMapping("/2via")
    @ResponseStatus(HttpStatus.CREATED)
    public Card2ViaResponse solicitar2via(@RequestBody Card2viaRequest request){
        return cardService.emitir2via(request);
    }

    @PatchMapping("/limit/{cardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CardNewLimiteCardResponse updateLimite(@PathVariable UUID cardId,@RequestBody Double newLimitCard){
        return cardService.uptadeLimitCard(cardId,newLimitCard);
    }
}

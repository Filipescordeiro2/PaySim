package com.paySim.payments.controller;

import com.paySim.payments.dto.request.UsuarioRequest;
import com.paySim.payments.dto.response.UsuarioResponse;
import com.paySim.payments.dto.response.UsuarioScoreResponse;
import com.paySim.payments.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse createUsuario(@RequestBody UsuarioRequest request){
        return usuarioService.createUsario(request);
    }

    @GetMapping("/ScoreUsuario/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioScoreResponse FindScore(@PathVariable String cpf){
        return usuarioService.findUsuarioScore(cpf);
    }
}

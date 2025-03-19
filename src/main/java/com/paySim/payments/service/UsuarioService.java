package com.paySim.payments.service;

import com.paySim.payments.domain.Usuario;
import com.paySim.payments.dto.request.UsuarioRequest;
import com.paySim.payments.dto.response.UsuarioResponse;
import com.paySim.payments.dto.response.UsuarioScoreResponse;
import com.paySim.payments.exception.UsuarioException;
import com.paySim.payments.repository.UsuarioRepository;
import com.paySim.payments.utils.UsuarioUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioUtils usuarioUtils;

    public UsuarioResponse createUsario(UsuarioRequest request){
        try{
            var usuario = new Usuario(request);
            var usuarioSaved = usuarioRepository.save(usuario);
            return usuarioUtils.convertToResponse(usuarioSaved);
        }catch (Exception e){
            throw new UsuarioException("Error creating user: "+e.getMessage());
        }
    }

    public UsuarioScoreResponse findUsuarioScore(String cpf){
        try{
            var usuario = usuarioRepository.findById(cpf)
                    .orElseThrow(() -> new UsuarioException("User not found"));
            return new UsuarioScoreResponse(usuario.getScore());
        }catch (Exception e){
            throw new UsuarioException("Error finding user score: "+e.getMessage());
        }
    }

}

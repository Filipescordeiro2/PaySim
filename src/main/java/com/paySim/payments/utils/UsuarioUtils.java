package com.paySim.payments.utils;

import com.paySim.payments.domain.Usuario;
import com.paySim.payments.dto.response.UsuarioResponse;
import com.paySim.payments.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioUtils {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse convertToResponse(Usuario usuario){
        return UsuarioResponse.builder()
                .cpf(usuario.getCpf())
                .nameUsuario(usuario.getNameUsuario())
                .email(usuario.getEmail())
                .phone(usuario.getPhone())
                .score(usuario.getScore())
                .statusUsuario(usuario.getStatusUsuario())
                .createdAt(usuario.getCreatedAt())
                .updatedAt(usuario.getUpdatedAt())
                .build();
    }

    public Usuario findById(String cpf){
        return usuarioRepository.findById(cpf)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
}

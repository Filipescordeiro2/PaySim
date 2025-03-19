package com.paySim.payments.domain;

import com.paySim.payments.dto.request.UsuarioRequest;
import com.paySim.payments.enums.StatusUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @CPF
    private String cpf;

    private String nameUsuario;

    @Email
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int score;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Card> card;

    @PrePersist
    public void prePersist() {
        this.statusUsuario = StatusUsuario.ATIVO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.score = gerarScore();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Usuario(UsuarioRequest request){
        this.cpf = request.getCpf();
        this.nameUsuario = request.getNameUsuario();
        this.email = request.getEmail();
        this.phone = request.getPhone();
    }

    public int gerarScore() {
        Random random = new Random();
        return random.nextInt(1001);
    }
}

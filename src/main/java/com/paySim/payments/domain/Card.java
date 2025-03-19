package com.paySim.payments.domain;

import com.paySim.payments.enums.StatusCard;
import com.paySim.payments.enums.TypeCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String password;

    @Enumerated(EnumType.STRING)
    private TypeCard cardType;

    private Double limitAprove;

    private Double limitCard;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private StatusCard statusCard;

    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.statusCard = StatusCard.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

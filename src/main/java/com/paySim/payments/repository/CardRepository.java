package com.paySim.payments.repository;

import com.paySim.payments.domain.Card;
import com.paySim.payments.enums.StatusCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByUsuarioCpfAndStatusCard(String cpfUsuario,StatusCard statusCard);
}

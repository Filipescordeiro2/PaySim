package com.paySim.payments.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_entry")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String machineId;
    private LocalDateTime timestamp;
    private String endpoint;
    private boolean responseStatus;
}

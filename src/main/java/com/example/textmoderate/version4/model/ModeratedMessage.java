package com.example.textmoderate.version4.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "moderated_messages")
public class ModeratedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String msisdn;
    private String message;
    private LocalDateTime createdAt;
    private int status;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


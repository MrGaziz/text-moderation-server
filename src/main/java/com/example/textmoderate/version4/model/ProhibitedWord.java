package com.example.textmoderate.version4.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "prohibited_words")
public class ProhibitedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String word;

    public String getWord() {
        return word;
    }

}


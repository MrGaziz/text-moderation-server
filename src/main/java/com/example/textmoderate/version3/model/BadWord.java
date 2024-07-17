package com.example.textmoderate.version3.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BadWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    // Конструктор по умолчанию нужен для JPA
    public BadWord() {
    }

    // Конструктор для создания объекта со словом
    public BadWord(Long id, String word) {
        this.id = id;  // Этот параметр может быть null, если объект ещё не сохранён
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

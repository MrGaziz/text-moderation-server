package com.example.textmoderate.version4.model;

public class ModerationResponse {
    private ModerationResult data;

    // Конструктор
    public ModerationResponse(ModerationResult data) {
        this.data = data;
    }

    // Геттеры и сеттеры
    public ModerationResult getData() {
        return data;
    }

    public void setData(ModerationResult data) {
        this.data = data;
    }
}
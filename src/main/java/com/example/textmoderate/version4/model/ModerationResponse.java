package com.example.textmoderate.version4.model;

public class ModerationResponse {
    private ModerationResult data;

    public ModerationResponse(ModerationResult data) {
        this.data = data;
    }

    public ModerationResult getData() {
        return data;
    }

    public void setData(ModerationResult data) {
        this.data = data;
    }
}
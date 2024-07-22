package com.example.textmoderate.version4.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reject_reasons")
public class RejectReason {
    @Id
    private int id;
    private String reason;
    private String reasonKazakh;
    private String reasonRussian;

    public String getReason() {
        return reason;
    }

    public String getReasonKazakh() {
        return reasonKazakh;
    }

    public String getReasonRussian() {
        return reasonRussian;
    }
}


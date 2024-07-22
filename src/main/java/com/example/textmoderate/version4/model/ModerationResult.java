package com.example.textmoderate.version4.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationResult {
    @JsonProperty("is_approved")
    private boolean isApproved;
    private String reason;

    public ModerationResult(boolean isApproved, String reason) {
        this.isApproved = isApproved;
        this.reason = reason;
    }
    @JsonProperty("is_approved")
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}


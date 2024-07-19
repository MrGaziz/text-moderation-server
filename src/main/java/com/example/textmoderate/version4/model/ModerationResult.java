package com.example.textmoderate.version4.model;

public class ModerationResult {
    private boolean isApproved;
    private String reason;

    public ModerationResult(boolean isApproved, String reason) {
        this.isApproved = isApproved;
        this.reason = reason;
    }

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


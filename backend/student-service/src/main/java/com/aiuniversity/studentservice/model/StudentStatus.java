package com.aiuniversity.studentservice.model;

public enum StudentStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    GRADUATED,
    DROPPED;

    public static StudentStatus fromString(String status) {
        if (status == null || status.isBlank()) {
            return ACTIVE;
        }
        try {
            return StudentStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid student status: " + status + ". Allowed values: ACTIVE, INACTIVE, SUSPENDED, GRADUATED, DROPPED");
        }
    }
}

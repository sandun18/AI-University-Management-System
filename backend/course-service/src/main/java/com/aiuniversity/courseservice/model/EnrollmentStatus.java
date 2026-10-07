package com.aiuniversity.courseservice.model;

public enum EnrollmentStatus {
    ENROLLED,
    COMPLETED,
    DROPPED,
    FAILED;

    public static EnrollmentStatus fromString(String status) {
        if (status == null || status.isBlank()) {
            return ENROLLED;
        }
        try {
            return EnrollmentStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid enrollment status: " + status + ". Allowed values: ENROLLED, COMPLETED, DROPPED, FAILED"
            );
        }
    }
}

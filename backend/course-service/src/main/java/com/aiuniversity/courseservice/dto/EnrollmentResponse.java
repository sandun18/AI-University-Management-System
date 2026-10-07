package com.aiuniversity.courseservice.dto;

import com.aiuniversity.courseservice.model.Course;
import com.aiuniversity.courseservice.model.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {

    private Long id;
    private Long studentId;
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private Integer credits;
    private String academicYear;
    private Integer semester;
    private String status;
    private String grade;
    private LocalDateTime enrolledAt;
    private LocalDateTime updatedAt;

    // ─── Factory Methods ─────────────────────────────────────────────────────────

    public static EnrollmentResponse from(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .academicYear(enrollment.getAcademicYear())
                .semester(enrollment.getSemester())
                .status(enrollment.getStatus() != null ? enrollment.getStatus().name() : null)
                .grade(enrollment.getGrade())
                .enrolledAt(enrollment.getEnrolledAt())
                .updatedAt(enrollment.getUpdatedAt())
                .build();
    }

    public static EnrollmentResponse from(Enrollment enrollment, Course course) {
        if (enrollment == null) {
            return null;
        }
        EnrollmentResponse response = from(enrollment);
        if (course != null) {
            response.setCourseCode(course.getCourseCode());
            response.setCourseTitle(course.getTitle());
            response.setCredits(course.getCredits());
        }
        return response;
    }
}

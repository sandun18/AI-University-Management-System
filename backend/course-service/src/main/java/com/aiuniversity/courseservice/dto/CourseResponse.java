package com.aiuniversity.courseservice.dto;

import com.aiuniversity.courseservice.model.Course;
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
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String title;
    private String description;
    private Integer credits;
    private String department;
    private Integer yearOfStudy;
    private Integer semester;
    private Integer maxCapacity;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ─── Factory Method ───────────────────────────────────────────────────────────

    public static CourseResponse from(Course course) {
        if (course == null) {
            return null;
        }
        return CourseResponse.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .title(course.getTitle())
                .description(course.getDescription())
                .credits(course.getCredits())
                .department(course.getDepartment())
                .yearOfStudy(course.getYearOfStudy())
                .semester(course.getSemester())
                .maxCapacity(course.getMaxCapacity())
                .isActive(course.getIsActive())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}

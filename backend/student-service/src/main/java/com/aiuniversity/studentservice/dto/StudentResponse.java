package com.aiuniversity.studentservice.dto;

import com.aiuniversity.studentservice.model.Student;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentResponse {

    private Long id;
    private String studentId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String department;
    private String degreeProgram;
    private Integer yearOfStudy;
    private Integer semester;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ─── Factory Method ───────────────────────────────────────────────────────────

    public static StudentResponse from(Student student) {
        if (student == null) {
            return null;
        }
        StudentResponse response = new StudentResponse();
        response.id            = student.getId();
        response.studentId     = student.getStudentId();
        response.userId        = student.getUserId();
        response.firstName     = student.getFirstName();
        response.lastName      = student.getLastName();
        response.email         = student.getEmail();
        response.phone         = student.getPhone();
        response.dateOfBirth   = student.getDateOfBirth();
        response.gender        = student.getGender();
        response.address       = student.getAddress();
        response.department    = student.getDepartment();
        response.degreeProgram = student.getDegreeProgram();
        response.yearOfStudy   = student.getYearOfStudy();
        response.semester      = student.getSemester();
        response.status        = student.getStatus() != null ? student.getStatus().name() : null;
        response.createdAt     = student.getCreatedAt();
        response.updatedAt     = student.getUpdatedAt();
        return response;
    }

    // ─── Constructors ────────────────────────────────────────────────────────────

    public StudentResponse() {
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

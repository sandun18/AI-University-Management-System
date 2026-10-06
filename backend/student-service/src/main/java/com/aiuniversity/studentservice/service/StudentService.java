package com.aiuniversity.studentservice.service;

import com.aiuniversity.studentservice.dto.StudentRequest;
import com.aiuniversity.studentservice.model.Student;
import com.aiuniversity.studentservice.model.StudentStatus;
import com.aiuniversity.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // ─── Create Student ───────────────────────────────────────────────────────────

    @Transactional
    public Student createStudent(StudentRequest request) {
        log.info("Creating student with student ID: {}, email: {}", request.getStudentId(), request.getEmail());
        validateStudentRequest(request, null);

        Student student = Student.builder()
                .studentId(request.getStudentId().trim())
                .userId(request.getUserId())
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .phone(request.getPhone() != null ? request.getPhone().trim() : null)
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender() != null ? request.getGender().trim() : null)
                .address(request.getAddress() != null ? request.getAddress().trim() : null)
                .department(request.getDepartment().trim())
                .degreeProgram(request.getDegreeProgram().trim())
                .yearOfStudy(request.getYearOfStudy())
                .semester(request.getSemester())
                .status(request.getStatus() != null && !request.getStatus().isBlank()
                        ? StudentStatus.fromString(request.getStatus())
                        : StudentStatus.ACTIVE)
                .build();

        Student saved = studentRepository.save(student);
        log.info("Successfully created student with ID: {} and student ID: {}", saved.getId(), saved.getStudentId());
        return saved;
    }

    // ─── Get All Students ─────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ─── Get Student By Primary Key ID ───────────────────────────────────────────

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }

    // ─── Get Student By Unique Registration Student ID ───────────────────────────

    @Transactional(readOnly = true)
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId.trim())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with student ID: " + studentId));
    }

    // ─── Get Student By Auth User ID ──────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found for user ID: " + userId));
    }

    // ─── Get Students By Department ───────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    // ─── Get Students By Status ───────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> getStudentsByStatus(String status) {
        return getStudentsByStatus(StudentStatus.fromString(status));
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsByStatus(StudentStatus status) {
        return studentRepository.findByStatus(status);
    }

    // ─── Get Students By Degree Program ───────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> getStudentsByDegreeProgram(String degreeProgram) {
        return studentRepository.findByDegreeProgram(degreeProgram);
    }

    // ─── Get Students By Department & Year ────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartmentAndYear(String department, Integer yearOfStudy) {
        return studentRepository.findByDepartmentAndYearOfStudy(department, yearOfStudy);
    }

    // ─── Search Students (By Keyword) ─────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllStudents();
        }
        return studentRepository.searchStudents(keyword.trim());
    }

    // ─── Update Student ───────────────────────────────────────────────────────────

    @Transactional
    public Student updateStudent(Long id, StudentRequest request) {
        log.info("Updating student ID: {}", id);
        Student student = getStudentById(id);

        validateStudentRequest(request, id);

        student.setStudentId(request.getStudentId().trim());
        if (request.getUserId() != null) {
            student.setUserId(request.getUserId());
        }
        student.setFirstName(request.getFirstName().trim());
        student.setLastName(request.getLastName().trim());
        student.setEmail(request.getEmail().trim().toLowerCase());
        student.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender() != null ? request.getGender().trim() : null);
        student.setAddress(request.getAddress() != null ? request.getAddress().trim() : null);
        student.setDepartment(request.getDepartment().trim());
        student.setDegreeProgram(request.getDegreeProgram().trim());
        student.setYearOfStudy(request.getYearOfStudy());
        student.setSemester(request.getSemester());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            student.setStatus(StudentStatus.fromString(request.getStatus()));
        }

        Student updated = studentRepository.save(student);
        log.info("Successfully updated student ID: {}", updated.getId());
        return updated;
    }

    // ─── Update Student Status ────────────────────────────────────────────────────

    @Transactional
    public Student updateStudentStatus(Long id, String status) {
        return updateStudentStatus(id, StudentStatus.fromString(status));
    }

    @Transactional
    public Student updateStudentStatus(Long id, StudentStatus status) {
        log.info("Updating status for student ID: {} to {}", id, status);
        Student student = getStudentById(id);
        student.setStatus(status);
        Student updated = studentRepository.save(student);
        log.info("Status updated for student ID: {} -> {}", id, status);
        return updated;
    }

    // ─── Delete Student ───────────────────────────────────────────────────────────

    @Transactional
    public void deleteStudent(Long id) {
        log.warn("Deleting student ID: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
        log.info("Successfully deleted student ID: {}", id);
    }

    // ─── Counts & Statistics ─────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public long countStudentsByStatus(StudentStatus status) {
        return studentRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public long countStudentsByDepartment(String department) {
        return studentRepository.countByDepartment(department);
    }

    // ─── Validation Helper ────────────────────────────────────────────────────────

    private void validateStudentRequest(StudentRequest request, Long currentId) {
        if (request.getStudentId() == null || request.getStudentId().isBlank()) {
            throw new IllegalArgumentException("Student ID is required");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Validate Student ID uniqueness
        studentRepository.findByStudentId(request.getStudentId().trim())
                .ifPresent(existing -> {
                    if (currentId == null || !existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException("Student ID already exists: " + request.getStudentId());
                    }
                });

        // Validate Email uniqueness
        studentRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .ifPresent(existing -> {
                    if (currentId == null || !existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException("Email already exists: " + request.getEmail());
                    }
                });

        // Validate UserId uniqueness if linked
        if (request.getUserId() != null) {
            studentRepository.findByUserId(request.getUserId())
                    .ifPresent(existing -> {
                        if (currentId == null || !existing.getId().equals(currentId)) {
                            throw new IllegalArgumentException("User account is already linked to student profile ID: " + existing.getStudentId());
                        }
                    });
        }

        // Validate Academic Year
        if (request.getYearOfStudy() != null && (request.getYearOfStudy() < 1 || request.getYearOfStudy() > 6)) {
            throw new IllegalArgumentException("Year of study must be between 1 and 6");
        }

        // Validate Semester
        if (request.getSemester() != null && (request.getSemester() < 1 || request.getSemester() > 3)) {
            throw new IllegalArgumentException("Semester must be between 1 and 3");
        }

        // Validate Date of Birth
        if (request.getDateOfBirth() != null) {
            if (request.getDateOfBirth().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Date of birth must be a past date");
            }
            int age = Period.between(request.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 14) {
                throw new IllegalArgumentException("Student age must be at least 14 years old");
            }
        }
    }
}

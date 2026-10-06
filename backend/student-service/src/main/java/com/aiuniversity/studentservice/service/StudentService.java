package com.aiuniversity.studentservice.service;

import com.aiuniversity.studentservice.dto.StudentRequest;
import com.aiuniversity.studentservice.model.Student;
import com.aiuniversity.studentservice.model.StudentStatus;
import com.aiuniversity.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // ─── Constructor Injection ────────────────────────────────────────────────────

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ─── Create Student ───────────────────────────────────────────────────────────

    public Student createStudent(StudentRequest request) {
        if (studentRepository.existsByStudentId(request.getStudentId())) {
            throw new IllegalArgumentException("Student ID already exists: " + request.getStudentId());
        }

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        Student student = new Student();
        student.setStudentId(request.getStudentId());
        student.setUserId(request.getUserId());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setDepartment(request.getDepartment());
        student.setDegreeProgram(request.getDegreeProgram());
        student.setYearOfStudy(request.getYearOfStudy());
        student.setSemester(request.getSemester());
        student.setStatus(request.getStatus() != null && !request.getStatus().isBlank()
                ? StudentStatus.fromString(request.getStatus())
                : StudentStatus.ACTIVE);

        return studentRepository.save(student);
    }

    // ─── Get All Students ─────────────────────────────────────────────────────────

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ─── Get Student By Primary Key ID ───────────────────────────────────────────

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }

    // ─── Get Student By Unique Registration Student ID ───────────────────────────

    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with student ID: " + studentId));
    }

    // ─── Get Student By Auth User ID ──────────────────────────────────────────────

    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found for user ID: " + userId));
    }

    // ─── Get Students By Department ───────────────────────────────────────────────

    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    // ─── Get Students By Status ───────────────────────────────────────────────────

    public List<Student> getStudentsByStatus(String status) {
        return studentRepository.findByStatus(StudentStatus.fromString(status));
    }

    public List<Student> getStudentsByStatus(StudentStatus status) {
        return studentRepository.findByStatus(status);
    }

    // ─── Update Student ───────────────────────────────────────────────────────────

    public Student updateStudent(Long id, StudentRequest request) {
        Student student = getStudentById(id);

        // Check if studentId changed and is already taken by another student
        if (!student.getStudentId().equalsIgnoreCase(request.getStudentId())
                && studentRepository.existsByStudentId(request.getStudentId())) {
            throw new IllegalArgumentException("Student ID already exists: " + request.getStudentId());
        }

        // Check if email changed and is already taken by another student
        if (!student.getEmail().equalsIgnoreCase(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        student.setStudentId(request.getStudentId());
        if (request.getUserId() != null) {
            student.setUserId(request.getUserId());
        }
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setDepartment(request.getDepartment());
        student.setDegreeProgram(request.getDegreeProgram());
        student.setYearOfStudy(request.getYearOfStudy());
        student.setSemester(request.getSemester());
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            student.setStatus(StudentStatus.fromString(request.getStatus()));
        }

        return studentRepository.save(student);
    }

    // ─── Update Student Status ────────────────────────────────────────────────────

    public Student updateStudentStatus(Long id, String status) {
        Student student = getStudentById(id);
        student.setStatus(StudentStatus.fromString(status));
        return studentRepository.save(student);
    }

    public Student updateStudentStatus(Long id, StudentStatus status) {
        Student student = getStudentById(id);
        student.setStatus(status);
        return studentRepository.save(student);
    }

    // ─── Delete Student ───────────────────────────────────────────────────────────

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}

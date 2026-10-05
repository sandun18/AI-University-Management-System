package com.aiuniversity.studentservice.controller;

import com.aiuniversity.studentservice.dto.StudentRequest;
import com.aiuniversity.studentservice.dto.StudentResponse;
import com.aiuniversity.studentservice.model.Student;
import com.aiuniversity.studentservice.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // ─── Constructor Injection ────────────────────────────────────────────────────

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ─── GET /api/students/health ──────────────────────────────────────────────────

    @GetMapping("/health")
    public String health() {
        return "Student Service is running";
    }

    // ─── POST /api/students ───────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest request) {
        try {
            Student student = studentService.createStudent(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(StudentResponse.from(student));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    // ─── GET /api/students ────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents()
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/{id} ───────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(StudentResponse.from(student));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    // ─── GET /api/students/registration/{studentId} ───────────────────────────────

    @GetMapping("/registration/{studentId}")
    public ResponseEntity<?> getStudentByStudentId(@PathVariable String studentId) {
        try {
            Student student = studentService.getStudentByStudentId(studentId);
            return ResponseEntity.ok(StudentResponse.from(student));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    // ─── GET /api/students/user/{userId} ──────────────────────────────────────────

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getStudentByUserId(@PathVariable Long userId) {
        try {
            Student student = studentService.getStudentByUserId(userId);
            return ResponseEntity.ok(StudentResponse.from(student));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    // ─── GET /api/students/department/{department} ────────────────────────────────

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartment(@PathVariable String department) {
        List<StudentResponse> students = studentService.getStudentsByDepartment(department)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/status/{status} ────────────────────────────────────────

    @GetMapping("/status/{status}")
    public ResponseEntity<List<StudentResponse>> getStudentsByStatus(@PathVariable String status) {
        List<StudentResponse> students = studentService.getStudentsByStatus(status)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── PUT /api/students/{id} ───────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        try {
            Student updated = studentService.updateStudent(id, request);
            return ResponseEntity.ok(StudentResponse.from(updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    // ─── PATCH /api/students/{id}/status ──────────────────────────────────────────

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStudentStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Student updated = studentService.updateStudentStatus(id, status);
            return ResponseEntity.ok(StudentResponse.from(updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    // ─── DELETE /api/students/{id} ────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully with ID: " + id);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
}

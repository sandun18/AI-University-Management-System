package com.aiuniversity.studentservice.controller;

import com.aiuniversity.studentservice.dto.StudentRequest;
import com.aiuniversity.studentservice.dto.StudentResponse;
import com.aiuniversity.studentservice.model.Student;
import com.aiuniversity.studentservice.model.StudentStatus;
import com.aiuniversity.studentservice.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // ─── GET /api/students/health ──────────────────────────────────────────────────

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "student-service"
        ));
    }

    // ─── POST /api/students ───────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        log.info("REST request to create student: {}", request.getStudentId());
        Student student = studentService.createStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StudentResponse.from(student));
    }

    // ─── GET /api/students ────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        log.info("REST request to get all students");
        List<StudentResponse> students = studentService.getAllStudents()
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/{id} ───────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        log.info("REST request to get student by ID: {}", id);
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(StudentResponse.from(student));
    }

    // ─── GET /api/students/registration/{studentId} ───────────────────────────────

    @GetMapping("/registration/{studentId}")
    public ResponseEntity<StudentResponse> getStudentByStudentId(@PathVariable String studentId) {
        log.info("REST request to get student by registration student ID: {}", studentId);
        Student student = studentService.getStudentByStudentId(studentId);
        return ResponseEntity.ok(StudentResponse.from(student));
    }

    // ─── GET /api/students/user/{userId} ──────────────────────────────────────────

    @GetMapping("/user/{userId}")
    public ResponseEntity<StudentResponse> getStudentByUserId(@PathVariable Long userId) {
        log.info("REST request to get student by Auth user ID: {}", userId);
        Student student = studentService.getStudentByUserId(userId);
        return ResponseEntity.ok(StudentResponse.from(student));
    }

    // ─── GET /api/students/search ─────────────────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> searchStudents(
            @RequestParam(name = "query", required = false) String query) {
        log.info("REST request to search students by keyword: {}", query);
        List<StudentResponse> students = studentService.searchStudents(query)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/department/{department} ────────────────────────────────

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartment(@PathVariable String department) {
        log.info("REST request to get students by department: {}", department);
        List<StudentResponse> students = studentService.getStudentsByDepartment(department)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/department/{department}/year/{yearOfStudy} ─────────────

    @GetMapping("/department/{department}/year/{yearOfStudy}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartmentAndYear(
            @PathVariable String department,
            @PathVariable Integer yearOfStudy) {
        log.info("REST request to get students by department: {} and year: {}", department, yearOfStudy);
        List<StudentResponse> students = studentService.getStudentsByDepartmentAndYear(department, yearOfStudy)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/program/{degreeProgram} ────────────────────────────────

    @GetMapping("/program/{degreeProgram}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDegreeProgram(@PathVariable String degreeProgram) {
        log.info("REST request to get students by degree program: {}", degreeProgram);
        List<StudentResponse> students = studentService.getStudentsByDegreeProgram(degreeProgram)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── GET /api/students/status/{status} ────────────────────────────────────────

    @GetMapping("/status/{status}")
    public ResponseEntity<List<StudentResponse>> getStudentsByStatus(@PathVariable String status) {
        log.info("REST request to get students by status: {}", status);
        List<StudentResponse> students = studentService.getStudentsByStatus(status)
                .stream()
                .map(StudentResponse::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    // ─── PUT /api/students/{id} ───────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        log.info("REST request to update student ID: {}", id);
        Student updated = studentService.updateStudent(id, request);
        return ResponseEntity.ok(StudentResponse.from(updated));
    }

    // ─── PATCH /api/students/{id}/status ──────────────────────────────────────────

    @PatchMapping("/{id}/status")
    public ResponseEntity<StudentResponse> updateStudentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        log.info("REST request to update status for student ID: {} to {}", id, status);
        Student updated = studentService.updateStudentStatus(id, status);
        return ResponseEntity.ok(StudentResponse.from(updated));
    }

    // ─── DELETE /api/students/{id} ────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        log.info("REST request to delete student ID: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Student deleted successfully",
                "id", id
        ));
    }

    // ─── GET /api/students/stats/status/{status} ──────────────────────────────────

    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Map<String, Object>> getStudentCountByStatus(@PathVariable String status) {
        StudentStatus studentStatus = StudentStatus.fromString(status);
        long count = studentService.countStudentsByStatus(studentStatus);
        return ResponseEntity.ok(Map.of(
                "status", studentStatus.name(),
                "count", count
        ));
    }

    // ─── GET /api/students/stats/department/{department} ──────────────────────────

    @GetMapping("/stats/department/{department}")
    public ResponseEntity<Map<String, Object>> getStudentCountByDepartment(@PathVariable String department) {
        long count = studentService.countStudentsByDepartment(department);
        return ResponseEntity.ok(Map.of(
                "department", department,
                "count", count
        ));
    }
}

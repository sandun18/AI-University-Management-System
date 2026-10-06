package com.aiuniversity.studentservice.repository;

import com.aiuniversity.studentservice.model.Student;
import com.aiuniversity.studentservice.model.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // ─── Find By Unique Attributes ──────────────────────────────────────────────

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByUserId(Long userId);

    // ─── Existence Checks ────────────────────────────────────────────────────────

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);

    boolean existsByUserId(Long userId);

    // ─── Filter & Categorization Queries ─────────────────────────────────────────

    List<Student> findByDepartment(String department);

    List<Student> findByDegreeProgram(String degreeProgram);

    List<Student> findByStatus(StudentStatus status);

    List<Student> findByDepartmentAndYearOfStudy(String department, Integer yearOfStudy);

    List<Student> findByDepartmentAndStatus(String department, StudentStatus status);

    // ─── Search ──────────────────────────────────────────────────────────────────

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchStudents(@Param("keyword") String keyword);

    // ─── Aggregate / Analytics Counts ────────────────────────────────────────────

    long countByStatus(StudentStatus status);

    long countByDepartment(String department);
}

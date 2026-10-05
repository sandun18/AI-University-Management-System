package com.aiuniversity.studentservice.repository;

import com.aiuniversity.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByUserId(Long userId);

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);

    List<Student> findByDepartment(String department);

    List<Student> findByDegreeProgram(String degreeProgram);

    List<Student> findByStatus(String status);

    List<Student> findByDepartmentAndYearOfStudy(String department, Integer yearOfStudy);
}

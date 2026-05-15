package com.springbootapplication.studentmanagement.repository;

import com.springbootapplication.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Optional<Student> findByRollNumber(Integer rollNumber);
    Optional<Student> findByName(String name);
}

package com.springbootapplication.studentmanagement.interfaces;

import com.springbootapplication.studentmanagement.dto.StudentRequestDTO;
import com.springbootapplication.studentmanagement.dto.StudentResponseDTO;
import com.springbootapplication.studentmanagement.dto.UpdateRequestDTO;

import java.util.List;
import java.util.Optional;

public interface StudentServices {
    List<StudentResponseDTO> getAllStudents();
    StudentResponseDTO addStudents(StudentRequestDTO dto);
    StudentResponseDTO updateStudents(Integer rollNumber, UpdateRequestDTO updateDto);
    void deleteStudent(Integer Id);
    Optional<StudentResponseDTO> findStudentById(Integer Id);
    Optional<StudentResponseDTO> findStudentByName(String name);
}

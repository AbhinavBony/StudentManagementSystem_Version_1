package com.springbootapplication.studentmanagement.service;

import com.springbootapplication.studentmanagement.dto.StudentRequestDTO;
import com.springbootapplication.studentmanagement.dto.StudentResponseDTO;
import com.springbootapplication.studentmanagement.dto.UpdateRequestDTO;
import com.springbootapplication.studentmanagement.entity.Student;
import com.springbootapplication.studentmanagement.exceptions.DuplicateStudentFound;
import com.springbootapplication.studentmanagement.exceptions.StudentNotFound;
import com.springbootapplication.studentmanagement.interfaces.StudentServices;
import com.springbootapplication.studentmanagement.repository.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentServices {
    private final StudentRepo repo;
    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        return repo.findAll().stream().map(student -> this.mapper.map(student, StudentResponseDTO.class)).toList();
    }

    @Override
    public StudentResponseDTO addStudents(StudentRequestDTO dto) {
        repo.findByRollNumber(dto.getRollNumber()).ifPresent((student)-> {
            throw new DuplicateStudentFound("Student already exists with roll number: "+ dto.getRollNumber());
        });
        Student student = this.mapper.map(dto, Student.class);
        Student saved = repo.save(student);
        return this.mapper.map(saved, StudentResponseDTO.class);
    }

    @Override
    public StudentResponseDTO updateStudents(Integer rollNumber, UpdateRequestDTO updateDto) {
        Student exists = repo.findByRollNumber(rollNumber).orElseThrow(()-> new StudentNotFound("Student not found with roll number : " + rollNumber));
        mapper.map(updateDto, exists);
        Student update = repo.save(exists);
        return this.mapper.map(update, StudentResponseDTO.class);
    }

    @Override
    public void deleteStudent(Integer Id) {
        repo.findById(Id).orElseThrow(()-> new StudentNotFound("Student not found with Id: "+ Id));
        repo.deleteById(Id);
    }

    @Override
    public Optional<StudentResponseDTO> findStudentById(Integer Id) {
        Student exists = repo.findById(Id).orElseThrow(()-> new StudentNotFound("Student not found with Id: "+ Id));
        return Optional.ofNullable(this.mapper.map(exists, StudentResponseDTO.class));
    }

    @Override
    public Optional<StudentResponseDTO> findStudentByName(String name) {
        Student existsByName = repo.findByName(name).orElseThrow(()-> new StudentNotFound("Student not found with name: "+ name));
        return Optional.ofNullable(this.mapper.map(existsByName, StudentResponseDTO.class));
    }
}
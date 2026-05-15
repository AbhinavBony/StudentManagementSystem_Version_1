package com.springbootapplication.studentmanagement.controller;

import com.springbootapplication.studentmanagement.dto.StudentRequestDTO;
import com.springbootapplication.studentmanagement.dto.StudentResponseDTO;
import com.springbootapplication.studentmanagement.dto.UpdateRequestDTO;
import com.springbootapplication.studentmanagement.interfaces.StudentServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/StudentManagement/v1")
@Validated
public class StudentController {
    private final StudentServices service;

    public StudentController(StudentServices service) {
        this.service = service;
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<StudentResponseDTO>> getAll(){
        List<StudentResponseDTO> studentList = service.getAllStudents();
        if (studentList.isEmpty()){
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(studentList);
    }
    @PostMapping(value = "/add")
    public ResponseEntity<StudentResponseDTO> add(@RequestBody @Valid StudentRequestDTO dto){
        StudentResponseDTO saved = service.addStudents(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping(value = "/update/{rollNumber}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable @Positive(message = "Roll Number Should Be Positive") @NotNull(message = "Roll Number Can't be Null") Integer rollNumber, @RequestBody @Valid UpdateRequestDTO dto){
        StudentResponseDTO updated = service.updateStudents(rollNumber, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping(value = "/delete/{Id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id Can't be negative") Integer Id){
        this.service.deleteStudent(Id);
        return ResponseEntity.ok("Student details deleted successfully!!");
    }
    @GetMapping(value = "/getById/{Id}")
    public ResponseEntity<Optional<StudentResponseDTO>> findById(@PathVariable @Positive(message = "Id Can't be negative")Integer Id){
        Optional<StudentResponseDTO> foundById = service.findStudentById(Id);
        return ResponseEntity.ok(foundById);
    }
    @GetMapping(value = "/getByName/{name}")
    public ResponseEntity<Optional<StudentResponseDTO>> findByName(@PathVariable @NotBlank(message = "Name Can't be empty/blank") String name){
        Optional<StudentResponseDTO> foundByName = service.findStudentByName(name);
        return ResponseEntity.ok(foundByName);
    }
}

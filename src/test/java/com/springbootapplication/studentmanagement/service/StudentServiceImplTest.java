package com.springbootapplication.studentmanagement.service;

import com.springbootapplication.studentmanagement.dto.StudentRequestDTO;
import com.springbootapplication.studentmanagement.dto.StudentResponseDTO;
import com.springbootapplication.studentmanagement.dto.UpdateRequestDTO;
import com.springbootapplication.studentmanagement.entity.Student;
import com.springbootapplication.studentmanagement.exceptions.DuplicateStudentFound;
import com.springbootapplication.studentmanagement.exceptions.StudentNotFound;
import com.springbootapplication.studentmanagement.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepo repo;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private StudentServiceImpl service;
    @Test
    void testGetAllStudents() {
        Student std1 =new Student();
        std1.setRollNumber(101);
        std1.setName("Abhinav");

        Student std2 = new Student();
        std2.setRollNumber(102);
        std2.setName("Rahul");
        List<Student> stdList = Arrays.asList(std1, std2);

        when(repo.findAll()).thenReturn(stdList);

        StudentResponseDTO dto1 = new StudentResponseDTO();
        dto1.setRollNumber(101);
        dto1.setName("Abhinav");
        StudentResponseDTO dto2 = new StudentResponseDTO();
        dto2.setRollNumber(102);
        dto2.setName("Rahul");

        when(mapper.map(std1, StudentResponseDTO.class)).thenReturn(dto1);
        when(mapper.map(std2, StudentResponseDTO.class)).thenReturn(dto2);

        List<StudentResponseDTO> list = service.getAllStudents();
        assertEquals(2, list.size());
        assertEquals("Rahul", list.get(1).getName());
    }

    @Test
    void testAddStudents() {
        StudentRequestDTO requestDTO = new StudentRequestDTO();
        requestDTO.setRollNumber(101);
        requestDTO.setName("Abhinav");

        Student student = new Student();
        student.setRollNumber(101);
        student.setName("Abhinav");

        Student saved = new Student();
        saved.setId(1);
        saved.setRollNumber(101);
        saved.setName("Abhinav");

        StudentResponseDTO responseDTO = new StudentResponseDTO();
        responseDTO.setId(1);
        responseDTO.setName("Abhinav");

        when(repo.findByRollNumber(101)).thenReturn(Optional.empty());
        when(mapper.map(requestDTO, Student.class)).thenReturn(student);
        when(repo.save(student)).thenReturn(saved);
        when(mapper.map(saved, StudentResponseDTO.class)).thenReturn(responseDTO);

        StudentResponseDTO result = service.addStudents(requestDTO);
        assertNotNull(result);
        assertEquals("Abhinav", result.getName());

    }
    @Test
    void testAddStudents_DuplicateStudentFound() {

        StudentRequestDTO requestDTO =
                new StudentRequestDTO();

        requestDTO.setRollNumber(101);

        Student student = new Student();

        when(repo.findByRollNumber(101))
                .thenReturn(Optional.of(student));

        assertThrows(DuplicateStudentFound.class,
                () -> service.addStudents(requestDTO));

        verify(repo, never()).save(any(Student.class));
    }

    @Test
    void testUpdateStudents() {

        Integer rollNumber = 101;

        UpdateRequestDTO updateDTO =
                new UpdateRequestDTO();

        updateDTO.setName("Updated Name");

        Student existingStudent = new Student();
        existingStudent.setRollNumber(101);
        existingStudent.setName("Old Name");

        Student updatedStudent = new Student();
        updatedStudent.setRollNumber(101);
        updatedStudent.setName("Updated Name");

        StudentResponseDTO responseDTO =
                new StudentResponseDTO();

        responseDTO.setName("Updated Name");

        when(repo.findByRollNumber(rollNumber))
                .thenReturn(Optional.of(existingStudent));

        // IMPORTANT FIX
        doNothing().when(mapper)
                .map(updateDTO, existingStudent);

        when(repo.save(existingStudent))
                .thenReturn(updatedStudent);

        when(mapper.map(updatedStudent,
                StudentResponseDTO.class))
                .thenReturn(responseDTO);

        StudentResponseDTO result =
                service.updateStudents(rollNumber, updateDTO);

        assertEquals("Updated Name", result.getName());
    }

    @Test
    void testUpdateStudents_StudentNotFound() {

        Integer rollNumber = 999;

        UpdateRequestDTO dto =
                new UpdateRequestDTO();

        when(repo.findByRollNumber(rollNumber))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFound.class,
                () -> service.updateStudents(
                        rollNumber, dto));
    }

    @Test
    void testDeleteStudent() {

        Integer id = 1;

        Student student = new Student();
        student.setId(id);

        when(repo.findById(id))
                .thenReturn(Optional.of(student));

        service.deleteStudent(id);

        verify(repo, times(1))
                .deleteById(id);
    }

    @Test
    void testDeleteStudent_StudentNotFound() {

        Integer id = 999;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFound.class,
                () -> service.deleteStudent(id));
    }

    @Test
    void testFindStudentById() {

        Integer id = 1;

        Student student = new Student();
        student.setId(id);
        student.setName("Abhinav");

        StudentResponseDTO responseDTO =
                new StudentResponseDTO();

        responseDTO.setId(id);
        responseDTO.setName("Abhinav");

        when(repo.findById(id))
                .thenReturn(Optional.of(student));

        when(mapper.map(student,
                StudentResponseDTO.class))
                .thenReturn(responseDTO);

        Optional<StudentResponseDTO> result =
                service.findStudentById(id);

        assertTrue(result.isPresent());
        assertEquals("Abhinav",
                result.get().getName());
    }

    @Test
    void testFindStudentById_StudentNotFound() {

        Integer id = 999;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFound.class,
                () -> service.findStudentById(id));
    }

    @Test
    void testFindStudentByName() {

        String name = "Abhinav";

        Student student = new Student();
        student.setName(name);

        StudentResponseDTO responseDTO =
                new StudentResponseDTO();

        responseDTO.setName(name);

        when(repo.findByName(name))
                .thenReturn(Optional.of(student));

        when(mapper.map(student,
                StudentResponseDTO.class))
                .thenReturn(responseDTO);

        Optional<StudentResponseDTO> result =
                service.findStudentByName(name);

        assertTrue(result.isPresent());
        assertEquals(name,
                result.get().getName());
    }

    @Test
    void testFindStudentByName_StudentNotFound() {

        String name = "Unknown";

        when(repo.findByName(name))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFound.class,
                () -> service.findStudentByName(name));
    }
}
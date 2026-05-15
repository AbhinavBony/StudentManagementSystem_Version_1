package com.springbootapplication.studentmanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Integer Id;
    private Integer rollNumber;
    private String name;
    private String schoolName;
}

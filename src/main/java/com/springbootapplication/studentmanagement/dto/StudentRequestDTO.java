package com.springbootapplication.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {
    @Positive(message = "RollNumber Should Be Positive")
    @NotNull(message = "RollNumber Can't be Blank")
    private Integer rollNumber;
    @NotBlank(message = "Student Name Can't be Blank")
    private String name;
    @NotBlank(message = "School Name Can't be Blank")
    private String schoolName;
}

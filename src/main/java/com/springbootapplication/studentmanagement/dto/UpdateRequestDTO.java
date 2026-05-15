package com.springbootapplication.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestDTO {
    @NotBlank(message = "Student Name Can't be Blank")
    private String name;
    @NotNull(message = "School Name Can't be Blank")
    private String schoolName;
}

package com.springbootapplication.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(name = "RollNumber", nullable = false)
    private Integer rollNumber;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "SchoolName", nullable = false)
    private String schoolName;
}

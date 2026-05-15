package com.springbootapplication.studentmanagement.exceptions;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound(String message) {
        super(message);
    }
}

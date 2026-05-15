package com.springbootapplication.studentmanagement.exceptions;

public class DuplicateStudentFound extends RuntimeException {
    public DuplicateStudentFound(String message) {
        super(message);
    }
}

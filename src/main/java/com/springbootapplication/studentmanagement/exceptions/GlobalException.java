package com.springbootapplication.studentmanagement.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateStudentFound.class)
    public ResponseEntity<ErrorResponse> handleDuplicateStudentFound(DuplicateStudentFound e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONTINUE.value(), e.getMessage(), LocalDateTime.now()));
    }
    @ExceptionHandler(StudentNotFound.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgsNotValid(MethodArgumentNotValidException ex){
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", errors);
        response.put("timeStamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodTypeMistMatch(MethodArgumentTypeMismatchException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", ex.getMessage());
        response.put("timeStamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.NOT_ACCEPTABLE.value());
        response.put("message", ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).findFirst().orElse("Validation Failed"));
        response.put("timeStamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", LocalDateTime.now()));
    }
}

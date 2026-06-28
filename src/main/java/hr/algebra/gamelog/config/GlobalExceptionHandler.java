package hr.algebra.gamelog.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.InvalidClassException;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidClassException.class)
    public ResponseEntity<String> handleInvalidClass(InvalidClassException e) {
        return ResponseEntity.status(400).body("Deserialization blocked: " + e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(400).body("File error: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}
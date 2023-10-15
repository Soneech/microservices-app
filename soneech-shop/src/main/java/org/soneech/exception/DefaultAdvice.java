package org.soneech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    private ResponseEntity<Map<String, String>> handleProductException(ProductException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler
    private ResponseEntity<Map<String, String>> handleCompanyException(CompanyException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }
}

package org.soneech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleAuthException(AuthException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleRoleException(RoleException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserException(UserException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }
}

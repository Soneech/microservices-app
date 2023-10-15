package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    public AuthException(String message) {
        super(message);
    }
}

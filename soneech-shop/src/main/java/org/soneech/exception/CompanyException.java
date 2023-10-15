package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CompanyException extends RuntimeException {
    private final HttpStatus status;
    public CompanyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

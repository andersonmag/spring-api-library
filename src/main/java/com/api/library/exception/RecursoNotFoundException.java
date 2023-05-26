package com.api.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNotFoundException extends RuntimeException {

    public RecursoNotFoundException(String message) {
        super(message);
    }
}
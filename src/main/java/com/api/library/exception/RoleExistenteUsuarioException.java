package com.api.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RoleExistenteUsuarioException extends RuntimeException{

    public RoleExistenteUsuarioException(String message) {
        super(message);
    }

}

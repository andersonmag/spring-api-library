package com.api.library.exception.handler;

import com.api.library.exception.EmailExistenteException;
import com.api.library.exception.RecursoNotFoundException;
import com.api.library.exception.RoleExistenteUsuarioException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManagementExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
       var erro = new ResponseError(status, ex.getMessage());
       return super.handleExceptionInternal(ex, erro, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        var campos = ex.getBindingResult().getAllErrors()
                                    .stream().map(campoErro -> new ResponseError.Campo(((FieldError) campoErro).getField(),
                                            campoErro.getDefaultMessage()))
                                    .collect(Collectors.toList());

        var erro = new ResponseError(status, campos);

        return super.handleExceptionInternal(ex, erro, headers, status, request);
    }

    @ExceptionHandler({ ValidationException.class, DataAccessException.class, SQLException.class })
    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseError error = new ResponseError(httpStatus, ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(RecursoNotFoundException.class)
    protected ResponseEntity<Object> handleExceptionRecursoNotFound(Exception ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseError error = new ResponseError(httpStatus, ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleExceptionContaBloqueadaLogin(AuthenticationException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ResponseError error = new ResponseError(httpStatus, obterMensagemErroLoginUsuario(ex));

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler({ EmailExistenteException.class, RoleExistenteUsuarioException.class })
    protected ResponseEntity<Object> handleExceptionConflict(Exception ex) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ResponseError error = new ResponseError(httpStatus, ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    private String obterMensagemErroLoginUsuario(AuthenticationException ex) {
        if (ex instanceof AccountStatusException) {
            return "Problemas com sua conta! Por favor, entre em contato com o administrador.";
        } else if (ex instanceof BadCredentialsException) {
            return "Dados de acesso incorretos!";
        } else {
            return "Não autorizado!";
        }
    }
}
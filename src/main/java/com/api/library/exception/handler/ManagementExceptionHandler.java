package com.api.library.exception.handler;

import com.api.library.exception.RecursoNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@ControllerAdvice
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

    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class, SQLException.class })
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ResponseError error = new ResponseError(status, ex.getMessage());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({RecursoNotFoundException.class})
    protected ResponseEntity<Object> handleExceptionRecursoNotFound(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ResponseError error = new ResponseError(status, ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
}
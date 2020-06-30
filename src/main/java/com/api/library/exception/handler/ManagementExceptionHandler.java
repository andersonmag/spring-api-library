package com.api.library.exception.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import com.api.library.exception.LivroNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.ObjectError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManagementExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ResponseError error = createResponseError(status, ex.getMessage());

         return super.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseError createResponseError(HttpStatus status, String message) {
        return new ResponseError(status, message);
    }

    @ExceptionHandler(LivroNotFoundException.class)
	public ResponseEntity<Object> handleExceptionLivroNotFound(LivroNotFoundException ex, WebRequest request) {
		ResponseError error = createResponseError(HttpStatus.NOT_FOUND, ex.getMessage());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ArrayList<ResponseError.Field> fields = new ArrayList<ResponseError.Field>();

        for (ObjectError errorFounded : ex.getBindingResult().getAllErrors()) {
            fields.add(new ResponseError.Field(((FieldError) errorFounded).getField(),
                                                 errorFounded.getDefaultMessage()));
        }

        ResponseError error = createResponseError(HttpStatus.BAD_REQUEST, ex.getMessage());
        error.setFields(fields);

        return super.handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    // Exceção quando o objeto não é contido na resposta do metodo
    @ExceptionHandler({ NoSuchElementException.class })
    protected ResponseEntity<Object> handleExceptionNoValuePresent(Exception ex) {
        ResponseError error = createResponseError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Exceção a nível de Banco de Dados
    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class, SQLException.class })
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

        ResponseError error = createResponseError(HttpStatus.BAD_REQUEST, 
                                                  ex.getCause().getCause().getMessage());
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
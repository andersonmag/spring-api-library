package com.api.library.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

@JsonInclude(value = Include.NON_NULL)
public class ResponseError {

    private int code;
    private String error;
    private String message;
    @JsonFormat(shape = Shape.STRING)
    private LocalDateTime time;
    private List<Field> fields;

    public ResponseError(HttpStatus status, String message) {
        this.code = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public ResponseError(HttpStatus status, String message, List<Field> fields) {
        this.code = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.fields = fields;
        this.time = LocalDateTime.now();
    }

    public ResponseError() {}

    public static class Field {

        private String name;
        private String message;

        public Field(String name, String message) {
            super();
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
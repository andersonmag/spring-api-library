package com.api.library.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

@JsonInclude(value = Include.NON_NULL)
public class ResponseError {

    private String status;
    private String message;
    private LocalDateTime time;
    private List<Field> fields;

    public ResponseError(HttpStatus status, String message) {
        this.status = status.value() + " " + status.getReasonPhrase();
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public ResponseError(HttpStatus status, String message,List<Field> fields) {
        this.status = status.value() + " " + status.getReasonPhrase();
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

    public String getStatus() {
        return status;
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

}
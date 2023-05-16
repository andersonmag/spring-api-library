package com.api.library.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    @AllArgsConstructor
    @Getter @Setter
    public static class Field {
        private String name;
        private String message;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
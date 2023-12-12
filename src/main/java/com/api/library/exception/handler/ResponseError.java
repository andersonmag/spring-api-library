package com.api.library.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(value = Include.NON_NULL)
@Getter
public class ResponseError {

    private int codigo;
    private String erro;
    private String mensagem;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime time;
    private List<Campo> campos;

    public ResponseError(HttpStatus status, String mensagem) {
        this.codigo = status.value();
        this.erro = status.getReasonPhrase();
        this.mensagem = mensagem;
        this.time = LocalDateTime.now();
    }

    public ResponseError(HttpStatus status, List<Campo> campos) {
        this.codigo = status.value();
        this.erro = status.getReasonPhrase();
        this.time = LocalDateTime.now();
        this.campos = campos;
    }

    @AllArgsConstructor
    @Getter
    public static class Campo {
        private String nome;
        private String mensagem;
    }
}
package com.api.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class LivroRequestDTO {
    @NotBlank
    private String titulo;
    @NotBlank
    private String autor;
    @NotBlank
    private String editora;
    @NotBlank @NumberFormat(pattern = "#,##0.00")
    private BigDecimal preco;
    @NotBlank
    private String descricao;
    @NotBlank
    private String idioma;
    @NotBlank @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPublicacao;
    @NotBlank @URL
    private String ImagemURL;
    @NotBlank
    private Integer idCategoria;
}

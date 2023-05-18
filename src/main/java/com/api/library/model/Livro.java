package com.api.library.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;

@Entity
@Getter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String autor;
    @Column(nullable = false)
    private String editora;
    @Column(nullable = false)
    private BigDecimal preco;
    private BigDecimal precoAnterior;
    
    @Lob
    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String idioma;

    @Column(nullable = false)
    private LocalDate dataPublicacao;

    @Column(nullable = false)
    private String ImagemURL;
    
    @OneToOne
    @Column(nullable = false)
    private Categoria categoria;
        
    @Column(length = 100, unique = true, updatable = false, nullable = false)
    private String link;

    @JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    
    @JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    public void setLink(String link) {
        this.link = link;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
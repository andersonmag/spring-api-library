package com.api.library.model;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        construirLink();
    }

    public void construirLink() {
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        this.link = pattern.matcher(Normalizer.normalize(this.titulo, Normalizer.Form.NFD)).replaceAll("");
        this.link = link.toLowerCase().replaceAll("[^a-zZ-Z1-9]", "-");
        this.link = link.replaceAll("--", "-");
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setPrecoAnterior(BigDecimal precoAnterior) {
        this.precoAnterior = precoAnterior;
    }


    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
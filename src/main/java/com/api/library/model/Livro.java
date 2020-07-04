package com.api.library.model;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    @NotBlank
    private String titulo;
    
    @NotBlank
    private String autor;
    
    @NotBlank
    private String editora;
    
    @NotBlank
    private BigDecimal preco;

    private BigDecimal precoAnterior;
    
    @Lob
    private String descricao;
    
    @NotBlank
    private String idioma;
    
    @Column(length = 100, unique = true, updatable = false, nullable = true)
    private String link;
    
    @NotBlank
    private String dataPublicacao;
    
    @NotBlank
    private String ImagemURL;
    
    @OneToOne
    private Categoria categoria;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonProperty(access = Access.READ_ONLY)
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

    public String getLink() {
        return link;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPrecoAnterior() {
        return precoAnterior;
    }

    public void setPrecoAnterior(BigDecimal precoAnterior) {
        this.precoAnterior = precoAnterior;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagemURL() {
        return ImagemURL;
    }

    public void setImagemURL(String imagemURL) {
        ImagemURL = imagemURL;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
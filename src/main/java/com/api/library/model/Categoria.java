package com.api.library.model;

import java.text.Normalizer;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotBlank
    private String nome;

    @JsonProperty(access = Access.READ_ONLY)
    @Column(length = 100, unique = true, updatable = false, nullable = true)
    private String link;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void construirLink() {
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        this.link = pattern.matcher(Normalizer.normalize(this.nome, Normalizer.Form.NFD)).replaceAll("");
        this.link = link.toLowerCase().replaceAll("[^a-zZ-Z1-9]", "-");
        this.link = link.replaceAll("--", "-");
    }
}
package com.api.library.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = Access.READ_ONLY)
    @Column(unique = true)
    private int codigo;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonFormat(shape = Shape.STRING)
    private LocalDateTime data;

    private BigDecimal valorTotal;

    @ManyToOne(optional = false)
    @JsonIncludeProperties(value = {"id", "nome"})
    private Usuario usuario;

    @OneToMany
    @JoinTable(name = "pedido_livros",
               joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "id",
                                         table = "pedido", unique = true),
               inverseJoinColumns = @JoinColumn(name = "livro_id", referencedColumnName = "id",
                                                table = "livro", unique = true))
    @JsonIncludeProperties(value = {"id", "titulo", "preco", "link"})
    private List<Livro> livros;

    public Pedido(int codigo, LocalDateTime data, BigDecimal valorTotal, Usuario usuario, List<Livro> livros) {
        this.codigo = codigo;
        this.data = data;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
        this.livros = livros;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
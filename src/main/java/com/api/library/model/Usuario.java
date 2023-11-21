package com.api.library.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import com.api.library.dto.UsuarioRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@Getter
@Entity
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinTable(name = "usuario_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "role_id"}, name = "pk_usuario_roles"),
                joinColumns = @JoinColumn(name = "usuario_id", table = "usuario", referencedColumnName = "id", updatable = false),
                inverseJoinColumns = @JoinColumn(name = "role_id", table = "role", referencedColumnName = "id", updatable = false))
    private List<Role> roles;

    private boolean status = true;

    public Usuario(UsuarioRequestDTO usuarioRequestDTO) {
        BeanUtils.copyProperties(usuarioRequestDTO, this);
    }

    public Usuario(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Usuario(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
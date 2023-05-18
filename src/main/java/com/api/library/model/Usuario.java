package com.api.library.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", 
        joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id",
         table = "usuario", unique = true),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id",
         table = "role", unique = true, updatable = false))
    private List<Role> roles;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Pedido> pedidos;

    private boolean status = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
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
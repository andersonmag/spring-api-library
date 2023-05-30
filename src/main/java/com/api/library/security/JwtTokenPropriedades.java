package com.api.library.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter @Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtTokenPropriedades {
    private String secretKey;
    private String tokenPrefix;
    private String headerToken;
    private long expirationTime;

    public JwtTokenPropriedades() {
        this.headerToken = "Authorization";
    }
}

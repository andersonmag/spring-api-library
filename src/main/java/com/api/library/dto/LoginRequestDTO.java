package com.api.library.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginRequestDTO {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String senha;
}

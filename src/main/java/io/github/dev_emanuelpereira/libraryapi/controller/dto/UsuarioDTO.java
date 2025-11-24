package io.github.dev_emanuelpereira.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO (
        @NotBlank(message = "Campo obrigatório")
        String login,
        @Email (message = "Email invalido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        String senha,
        List<String> roles
) {
}

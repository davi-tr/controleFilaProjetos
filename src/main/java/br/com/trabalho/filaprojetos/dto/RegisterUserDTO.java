package br.com.trabalho.filaprojetos.dto;

import br.com.trabalho.filaprojetos.model.Role;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank String nome, @NotBlank String email, @NotBlank String password, Role role) {
}

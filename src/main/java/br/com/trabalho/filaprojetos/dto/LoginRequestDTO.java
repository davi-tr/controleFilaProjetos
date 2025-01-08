package br.com.trabalho.filaprojetos.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequestDTO(@NotBlank String email, @NotBlank String password) implements Serializable {
}

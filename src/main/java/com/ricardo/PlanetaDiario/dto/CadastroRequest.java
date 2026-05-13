package com.ricardo.PlanetaDiario.dto;

import com.ricardo.PlanetaDiario.entities.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroRequest(

        @NotBlank(message = "Nome obrigatório")
        @Size(min = 3, max = 100)
        String nome,

        @NotBlank(message = "Nome de usuário obrigatório")
        @Size(min = 3, max = 100)
        String username,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha,

        @NotNull(message = "Tipo de usuário obrigatório")
        TipoUsuario tipo,

        String profissao
) {}
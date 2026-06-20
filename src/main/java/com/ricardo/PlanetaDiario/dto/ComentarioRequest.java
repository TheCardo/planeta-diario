package com.ricardo.PlanetaDiario.dto;

import jakarta.validation.constraints.NotBlank;

public record ComentarioRequest(
        @NotBlank(message = "O texto do comentário não pode ser vazio")
        String texto,

        @NotBlank(message = "A URL da notícia é obrigatória")
        String urlNoticia
) {}
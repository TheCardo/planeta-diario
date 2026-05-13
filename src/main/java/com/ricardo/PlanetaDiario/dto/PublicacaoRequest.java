package com.ricardo.PlanetaDiario.dto;

import jakarta.validation.constraints.NotBlank;

public record PublicacaoRequest(
        @NotBlank String titulo,
        @NotBlank String conteudo,
        @NotBlank String noticiaUrlRef
) {}
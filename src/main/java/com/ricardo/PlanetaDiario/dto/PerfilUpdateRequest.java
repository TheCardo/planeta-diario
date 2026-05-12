package com.ricardo.PlanetaDiario.dto;

public record PerfilUpdateRequest(
        String bio,
        String fotoUrl,
        String profissao
) {}
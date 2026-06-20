package com.ricardo.PlanetaDiario.dto;

public record PerfilUpdateRequest(
        String nome,
        String bio,
        String fotoUrl,
        String profissao
) {}
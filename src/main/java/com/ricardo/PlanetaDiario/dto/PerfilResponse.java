package com.ricardo.PlanetaDiario.dto;

import com.ricardo.PlanetaDiario.entities.enums.TipoUsuario;
import java.time.LocalDateTime;
import java.util.UUID;

public record PerfilResponse(
        UUID id,
        String nome,
        String username,
        String email,
        String bio,
        String fotoUrl,
        String profissao,
        TipoUsuario tipo,
        LocalDateTime dataDeCriacao
) {}
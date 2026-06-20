package com.ricardo.PlanetaDiario.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComentarioResponse(
        UUID id,
        String texto,
        String urlNoticia,
        String autorUsername,
        LocalDateTime dataCriacao
) {}
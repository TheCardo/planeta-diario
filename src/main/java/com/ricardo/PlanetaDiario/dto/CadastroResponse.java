package com.ricardo.PlanetaDiario.dto;

import com.ricardo.PlanetaDiario.entities.enums.TipoUsuario;
import java.util.UUID;

public record CadastroResponse(
        UUID id,
        String username,
        String email,
        TipoUsuario tipo
) {}
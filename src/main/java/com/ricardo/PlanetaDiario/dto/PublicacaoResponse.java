package com.ricardo.PlanetaDiario.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PublicacaoResponse(
        UUID id,
        String titulo,
        String conteudo,
        String autorNome,
        String autorUsername,
        String autorProfissao,
        LocalDateTime dataDeCriacao,
        String noticiaUrlRef
) {}
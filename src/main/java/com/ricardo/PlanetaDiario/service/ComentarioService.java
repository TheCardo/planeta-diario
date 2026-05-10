package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.ComentarioRequest;
import com.ricardo.PlanetaDiario.dto.ComentarioResponse;

import java.util.List;
import java.util.UUID;

public interface ComentarioService {
    ComentarioResponse adicionarComentario(ComentarioRequest request, UUID usuarioId);
    List<ComentarioResponse> buscarPorNoticia(String urlNoticia);
}
package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.PublicacaoRequest;
import com.ricardo.PlanetaDiario.dto.PublicacaoResponse;
import java.util.List;
import java.util.UUID;

public interface PublicacaoService {
    void criarPublicacao(PublicacaoRequest request, UUID autorId);
    List<PublicacaoResponse> buscarPorNoticia(String noticiaUrl);
    List<PublicacaoResponse> buscarMeusDossies(UUID autorId);
}
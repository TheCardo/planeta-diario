package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.client.GnewsClient;
import com.ricardo.PlanetaDiario.dto.NoticiaResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service

public class GnewsServiceImpl implements NoticiaService {

    private final GnewsClient gnewsClient;
    public GnewsServiceImpl(GnewsClient gnewsClient) {
        this.gnewsClient = gnewsClient;
    }

    @Override
    public NoticiaResponse buscarNoticiasPorTermo(String termo) {
        return gnewsClient.getNoticiasPorTermoDeCategoria(termo);
    }

    @Override
    @Cacheable(value = "topHeadlines", key = "#categoria")
    public NoticiaResponse buscarNoticiasPorCategoriasEmAlta(String categoria){
        return gnewsClient.getNoticiasPorCategoriasEmAlta(categoria);
    }
}

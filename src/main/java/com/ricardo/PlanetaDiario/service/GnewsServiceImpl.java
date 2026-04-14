package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.client.GnewsClient;
import com.ricardo.PlanetaDiario.dto.NoticiaRespostaDTO;
import org.springframework.stereotype.Service;

@Service
public class GnewsServiceImpl implements NoticiaService {

    private final GnewsClient gnewsClient;
    public GnewsServiceImpl(GnewsClient gnewsClient) {
        this.gnewsClient = gnewsClient;
    }

    @Override
    public NoticiaRespostaDTO buscarNoticiasPorTermo(String termo) {
        return gnewsClient.getNoticiasPorTermoDeCategoria(termo);
    }

    @Override
    public NoticiaRespostaDTO buscarNoticiasPorCategoriasEmAlta(String categoria){
        return gnewsClient.getNoticiasPorCategoriasEmAlta(categoria);
    }
}

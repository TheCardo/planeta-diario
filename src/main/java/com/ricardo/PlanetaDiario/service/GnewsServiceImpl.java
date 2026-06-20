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
    public NoticiaResponse buscarNoticiasPorCategoriasEmAlta(String categoria) {
        String categoriaIngles = converterCategoriaParaIngles(categoria);
        return gnewsClient.getNoticiasPorCategoriasEmAlta(categoriaIngles);
    }

    private String converterCategoriaParaIngles(String categoria) {
        if (categoria == null) {
            return "general";
        }

        return switch (categoria.toLowerCase().trim()) {
            case "geral" -> "general";
            case "mundo" -> "world";
            case "nação", "nacao" -> "nation";
            case "negócios", "negocios" -> "business";
            case "tecnologia" -> "technology";
            case "entretenimento" -> "entertainment";
            case "esportes" -> "sports";
            case "ciência", "ciencia" -> "science";
            case "saúde", "saude" -> "health";
            default -> categoria.toLowerCase();
        };
    }
}
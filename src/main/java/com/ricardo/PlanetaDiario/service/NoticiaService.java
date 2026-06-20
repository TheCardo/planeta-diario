package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.NoticiaResponse;

public interface NoticiaService {

    NoticiaResponse buscarNoticiasPorTermo(String termo);
    NoticiaResponse buscarNoticiasPorCategoriasEmAlta(String categoria);

}

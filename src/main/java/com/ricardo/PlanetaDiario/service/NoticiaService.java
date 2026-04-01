package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.NoticiaRespostaDTO;

public interface NoticiaService {

    NoticiaRespostaDTO buscarNoticiasPorTermo(String termo);

}

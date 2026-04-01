package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.NoticiaRespostaDTO;
import com.ricardo.PlanetaDiario.service.NoticiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NoticiaController {
    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }


    @GetMapping("/busca/{termo}")
    public ResponseEntity<NoticiaRespostaDTO> procurarArtigos(@PathVariable String termo) {

        NoticiaRespostaDTO artigos = noticiaService.buscarNoticiasPorTermo(termo);

        if (artigos != null) {
            return ResponseEntity.ok(artigos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
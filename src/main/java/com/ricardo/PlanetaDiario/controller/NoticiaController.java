package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.NoticiaResponse;
import com.ricardo.PlanetaDiario.service.NoticiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
@CrossOrigin(origins = "http://localhost:5173")

public class NoticiaController {
    private final NoticiaService noticiaService;

//    public NoticiaController(NoticiaService noticiaService) {
//        this.noticiaService = noticiaService;
//    }


    @GetMapping("/busca/{termo}")
    public ResponseEntity<NoticiaResponse> procurarArtigos(@PathVariable String termo) {

        NoticiaResponse artigos = noticiaService.buscarNoticiasPorTermo(termo);

        if (artigos != null) {
            return ResponseEntity.ok(artigos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/top/{categoria}")
    public ResponseEntity<NoticiaResponse> buscarTopHeadlines(@PathVariable String categoria){

        NoticiaResponse artigos = noticiaService.buscarNoticiasPorCategoriasEmAlta(categoria);

        if (artigos != null){
            return ResponseEntity.ok(artigos);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

}
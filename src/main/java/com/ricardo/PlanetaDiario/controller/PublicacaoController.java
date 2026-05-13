package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.PublicacaoRequest;
import com.ricardo.PlanetaDiario.dto.PublicacaoResponse;
import com.ricardo.PlanetaDiario.service.PublicacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publicacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacaoController {

    private final PublicacaoService publicacaoService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody PublicacaoRequest request, JwtAuthenticationToken token) {
        UUID autorId = UUID.fromString(token.getName());
        publicacaoService.criarPublicacao(request, autorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/noticia")
    public ResponseEntity<List<PublicacaoResponse>> buscarPorNoticia(@RequestParam String url) {
        return ResponseEntity.ok(publicacaoService.buscarPorNoticia(url));
    }

    @GetMapping("/meus-dossies")
    public ResponseEntity<List<PublicacaoResponse>> buscarMeusDossies(JwtAuthenticationToken token) {
        UUID autorId = UUID.fromString(token.getName());
        return ResponseEntity.ok(publicacaoService.buscarMeusDossies(autorId));
    }
}
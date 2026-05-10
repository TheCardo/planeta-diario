package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.ComentarioRequest;
import com.ricardo.PlanetaDiario.dto.ComentarioResponse;
import com.ricardo.PlanetaDiario.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioResponse> criar(
            @RequestBody @Valid ComentarioRequest request,
            @AuthenticationPrincipal Jwt jwt // Captura o token validado
    ) {
        // Extrai o UUID do usuário que está dentro do Payload do JWT
        UUID usuarioId = UUID.fromString(jwt.getSubject());

        ComentarioResponse response = comentarioService.adicionarComentario(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> listarPorNoticia(@RequestParam String urlNoticia) {
        List<ComentarioResponse> respostas = comentarioService.buscarPorNoticia(urlNoticia);
        return ResponseEntity.ok(respostas);
    }
}
package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.ComentarioRequest;
import com.ricardo.PlanetaDiario.dto.ComentarioResponse;
import com.ricardo.PlanetaDiario.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://localhost:5173")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioResponse> criar(
            @RequestBody @Valid ComentarioRequest request,
            JwtAuthenticationToken token
    ) {
        UUID usuarioId = UUID.fromString(token.getName());

        ComentarioResponse response = comentarioService.adicionarComentario(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> listarPorNoticia(@RequestParam String urlNoticia) {
        List<ComentarioResponse> respostas = comentarioService.buscarPorNoticia(urlNoticia);
        return ResponseEntity.ok(respostas);
    }
}
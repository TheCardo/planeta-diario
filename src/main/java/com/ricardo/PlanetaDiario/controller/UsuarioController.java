package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.PerfilResponse;
import com.ricardo.PlanetaDiario.dto.PerfilUpdateRequest;
import com.ricardo.PlanetaDiario.entities.Usuario;
import com.ricardo.PlanetaDiario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/me")
    public ResponseEntity<PerfilResponse> obterMeuPerfil(JwtAuthenticationToken token) {
        Usuario usuario = usuarioRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado nos arquivos do Planeta Diário"));

        PerfilResponse response = new PerfilResponse(
                usuario.getId(), usuario.getUsername(), usuario.getEmail(),
                usuario.getBio(), usuario.getFotoUrl(), usuario.getProfissao(),
                usuario.getTipo(), usuario.getDataDeCriacao()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<PerfilResponse> atualizarPerfil(@RequestBody PerfilUpdateRequest request, JwtAuthenticationToken token) {
        Usuario usuario = usuarioRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setBio(request.bio());
        usuario.setFotoUrl(request.fotoUrl());

        if (usuario.getTipo().name().equals("CRIADOR_DE_CONTEUDO")) {
            usuario.setProfissao(request.profissao());
        }

        usuarioRepository.save(usuario);

        PerfilResponse response = new PerfilResponse(
                usuario.getId(), usuario.getUsername(), usuario.getEmail(),
                usuario.getBio(), usuario.getFotoUrl(), usuario.getProfissao(),
                usuario.getTipo(), usuario.getDataDeCriacao()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarConta(JwtAuthenticationToken token) {
        Usuario usuario = usuarioRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
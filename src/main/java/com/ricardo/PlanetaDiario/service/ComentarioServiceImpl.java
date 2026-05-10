package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.ComentarioRequest;
import com.ricardo.PlanetaDiario.dto.ComentarioResponse;
import com.ricardo.PlanetaDiario.entities.Comentario;
import com.ricardo.PlanetaDiario.entities.Usuario;
import com.ricardo.PlanetaDiario.repository.ComentarioRepository;
import com.ricardo.PlanetaDiario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ComentarioResponse adicionarComentario(ComentarioRequest request, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Comentario comentario = new Comentario();
        comentario.setConteudo(request.texto());
        comentario.setUrlDoArtigo(request.urlNoticia());
        comentario.setAutor(usuario);

        Comentario salvo = comentarioRepository.save(comentario);

        return new ComentarioResponse(
                salvo.getId(),
                salvo.getConteudo(),
                salvo.getUrlDoArtigo(),
                salvo.getAutor().getUsername(),
                salvo.getDataDeCriacao()
        );
    }

    @Override
    public List<ComentarioResponse> buscarPorNoticia(String urlNoticia) {
        return comentarioRepository.findByUrlDoArtigoOrderByDataDeCriacaoDesc(urlNoticia)
                .stream()
                .map(c -> new ComentarioResponse(
                        c.getId(),
                        c.getConteudo(),
                        c.getUrlDoArtigo(),
                        c.getAutor().getUsername(),
                        c.getDataDeCriacao()
                ))
                .collect(Collectors.toList());
    }
}
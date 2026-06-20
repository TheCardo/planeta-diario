package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.PublicacaoRequest;
import com.ricardo.PlanetaDiario.dto.PublicacaoResponse;
import com.ricardo.PlanetaDiario.entities.Publicacao;
import com.ricardo.PlanetaDiario.entities.Usuario;
import com.ricardo.PlanetaDiario.events.NovaPublicacaoEvent;
import com.ricardo.PlanetaDiario.repository.PublicacaoRepository;
import com.ricardo.PlanetaDiario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicacaoServiceImpl implements PublicacaoService {

    private final PublicacaoRepository publicacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    @CacheEvict(value = {"dossiesRecentes", "dossiesPorNoticia"}, allEntries = true)
    public void criarPublicacao(PublicacaoRequest request, UUID autorId) {
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Publicacao pub = new Publicacao();
        pub.setTitulo(request.titulo());
        pub.setConteudo(request.conteudo());
        pub.setNoticiaUrlRef(request.noticiaUrlRef());
        pub.setAutor(autor);

        publicacaoRepository.save(pub);

        eventPublisher.publishEvent(new NovaPublicacaoEvent(autor.getUsername(), pub.getTitulo()));
    }

    @Override
    @Cacheable(value = "dossiesPorNoticia", key = "#noticiaUrl")
    public List<PublicacaoResponse> buscarPorNoticia(String noticiaUrl) {
        return publicacaoRepository.findByNoticiaUrlRefOrderByDataDeCriacaoDesc(noticiaUrl)
                .stream()
                .map(pub -> new PublicacaoResponse(
                        pub.getId(),
                        pub.getTitulo(),
                        pub.getConteudo(),
                        pub.getAutor().getNome(),
                        pub.getAutor().getUsername(),
                        pub.getAutor().getProfissao(),
                        pub.getDataDeCriacao(),
                        pub.getNoticiaUrlRef()
                )).toList();
    }

    @Override
    public List<PublicacaoResponse> buscarMeusDossies(UUID autorId) {
        return publicacaoRepository.findByAutorIdOrderByDataDeCriacaoDesc(autorId)
                .stream().map(pub -> new PublicacaoResponse(
                        pub.getId(),
                        pub.getTitulo(),
                        pub.getConteudo(),
                        pub.getAutor().getNome(),
                        pub.getAutor().getUsername(),
                        pub.getAutor().getProfissao(),
                        pub.getDataDeCriacao(),
                        pub.getNoticiaUrlRef()
                )).toList();
    }
    @Override
    @Cacheable(value = "dossiesRecentes")
    public List<PublicacaoResponse> buscarRecentes() {
        return publicacaoRepository.findTop10ByOrderByDataDeCriacaoDesc()
                .stream().map(pub -> new PublicacaoResponse(
                        pub.getId(), pub.getTitulo(), pub.getConteudo(),
                        pub.getAutor().getNome(), pub.getAutor().getUsername(),
                        pub.getAutor().getProfissao(), pub.getDataDeCriacao(), pub.getNoticiaUrlRef()
                )).toList();
    }

    @Override
    public List<PublicacaoResponse> buscarDossiesPorAutor(String username) {
        Usuario autor = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        return publicacaoRepository.findByAutorIdOrderByDataDeCriacaoDesc(autor.getId())
                .stream().map(pub -> new PublicacaoResponse(
                        pub.getId(), pub.getTitulo(), pub.getConteudo(),
                        pub.getAutor().getNome(), pub.getAutor().getUsername(),
                        pub.getAutor().getProfissao(), pub.getDataDeCriacao(), pub.getNoticiaUrlRef()
                )).toList();
    }
}
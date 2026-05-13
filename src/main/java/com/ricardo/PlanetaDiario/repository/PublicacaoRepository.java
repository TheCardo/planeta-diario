package com.ricardo.PlanetaDiario.repository;

import com.ricardo.PlanetaDiario.entities.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PublicacaoRepository extends JpaRepository<Publicacao, UUID> {
    List<Publicacao> findByNoticiaUrlRefOrderByDataDeCriacaoDesc(String noticiaUrlRef);
    List<Publicacao> findByAutorIdOrderByDataDeCriacaoDesc(UUID autorId);
}
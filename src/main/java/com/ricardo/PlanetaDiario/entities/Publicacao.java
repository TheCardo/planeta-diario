package com.ricardo.PlanetaDiario.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "publicacoes")
@Data
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "publicacao_id")
    private UUID id;

    @NotBlank
    private String titulo;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @NotBlank
    private String noticiaUrlRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private LocalDateTime dataDeCriacao;

    @PrePersist
    public void prePersist() {
        this.dataDeCriacao = LocalDateTime.now();
    }
}
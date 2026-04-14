package com.ricardo.PlanetaDiario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "A URL do artigo é obrigatória")
    @Column(nullable = false)
    private String urlDoArtigo;

    @NotBlank(message = "O comentário não pode ser vazio")
    @Size(min = 10, max = 2000, message = "Comentário deve ter entre 10 e 2000 caracteres")
    @Column(nullable = false, length = 2000)
    private String conteudo;

    private LocalDateTime dataDeCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @PrePersist
    public void prePersist() {
        this.dataDeCriacao = LocalDateTime.now();
    }
}
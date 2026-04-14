package com.ricardo.PlanetaDiario.model;
import com.ricardo.PlanetaDiario.model.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;


@Entity
@Table(name = "usuarios")
@Data

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @NotBlank(message = "o nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Seu Username deve ter entre 3 e 100 caracteres")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "E-mail inválido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senha;

    private LocalDateTime dataDeCriacao;

    @PrePersist
    public void prePersist() {
        this.dataDeCriacao = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoUsuario tipo;

}

package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.entities.Usuario;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private static final long EXPIRACAO_EM_SEGUNDOS = 7200L;

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String gerarToken(Usuario usuario) {
        var agora = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("planeta-diario")
                .subject(usuario.getId().toString())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(EXPIRACAO_EM_SEGUNDOS))
                .claim("username", usuario.getUsername())
                .claim("tipo", usuario.getTipo().name())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public long getExpiracaoEmSegundos() {
        return EXPIRACAO_EM_SEGUNDOS;
    }
}
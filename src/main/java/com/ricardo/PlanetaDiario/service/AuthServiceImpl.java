package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.CadastroRequest;
import com.ricardo.PlanetaDiario.dto.CadastroResponse;
import com.ricardo.PlanetaDiario.dto.LoginRequest;
import com.ricardo.PlanetaDiario.dto.LoginResponse;
import com.ricardo.PlanetaDiario.entities.Usuario;
import com.ricardo.PlanetaDiario.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public CadastroResponse registrar(CadastroRequest request) {

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username já está em uso");
        }

        var usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha())); // NUNCA salvar senha em texto puro
        usuario.setTipo(request.tipo());

        var salvo = usuarioRepository.save(usuario);

        return new CadastroResponse(
                salvo.getId(),
                salvo.getUsername(),
                salvo.getEmail(),
                salvo.getTipo()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        var usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos"));

        boolean senhaCorreta = passwordEncoder.matches(request.password(), usuario.getSenha());
        if (!senhaCorreta) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(token, jwtService.getExpiracaoEmSegundos());
    }
}
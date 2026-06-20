package com.ricardo.PlanetaDiario.controller;

import com.ricardo.PlanetaDiario.dto.CadastroRequest;
import com.ricardo.PlanetaDiario.dto.CadastroResponse;
import com.ricardo.PlanetaDiario.dto.LoginRequest;
import com.ricardo.PlanetaDiario.dto.LoginResponse;
import com.ricardo.PlanetaDiario.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<CadastroResponse> registrar(@RequestBody @Valid CadastroRequest request) {
        CadastroResponse response = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
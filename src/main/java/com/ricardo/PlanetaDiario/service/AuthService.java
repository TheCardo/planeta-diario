package com.ricardo.PlanetaDiario.service;

import com.ricardo.PlanetaDiario.dto.CadastroRequest;
import com.ricardo.PlanetaDiario.dto.CadastroResponse;
import com.ricardo.PlanetaDiario.dto.LoginRequest;
import com.ricardo.PlanetaDiario.dto.LoginResponse;

public interface AuthService {
    CadastroResponse registrar(CadastroRequest request);
    LoginResponse login(LoginRequest request);
}
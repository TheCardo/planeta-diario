package com.ricardo.PlanetaDiario.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //? O RestTemplate é a ferramenta do Spring para fazer requisições HTTP (ir até outra API na internet e buscar dados).
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

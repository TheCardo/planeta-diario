package com.ricardo.PlanetaDiario.client;

import com.ricardo.PlanetaDiario.dto.GnewsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GnewsClient {

    private final RestTemplate restTemplate;
    private final String GNEWS_URL = "https://gnews.io/api/v4/";
    private final String apiKey;

    public GnewsClient(RestTemplate restTemplate, @Value("${gnews.api.key}") String apiKey){
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    // https://gnews.io/api/v4/top-headlines?category=general&apikey=API_KEY

    public GnewsResponseDTO getNoticiasPorCategorias(String categoria){

        String url = GNEWS_URL + "/top-headlines" + "?apikey=" + apiKey + "&category=" + categoria + "&lang=pt&country=br";
        try {
            return restTemplate.getForObject(url, GnewsResponseDTO.class);
        }catch (Exception e){
            System.err.println("erro ao acessar os artigos" + e.getMessage());
            return null;
        }
    }
}

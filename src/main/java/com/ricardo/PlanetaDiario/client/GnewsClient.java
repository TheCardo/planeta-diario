package com.ricardo.PlanetaDiario.client;

import com.ricardo.PlanetaDiario.dto.NoticiaRespostaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GnewsClient {

    private final RestTemplate restTemplate;
    private final String GNEWS_URL;
    private final String apiKey;

    public GnewsClient(RestTemplate restTemplate,
                       @Value("${endpoint.gnews}") String gnewsUrl,
                       @Value("${gnews.api.key}") String apiKey){
        this.restTemplate = restTemplate;
        GNEWS_URL = gnewsUrl;
        this.apiKey = apiKey;
    }

    // https://gnews.io/api/v4/top-headlines?category=general&apikey=API_KEY

//    Top Headlines Endpoint
//
//    Get trending articles based on Google News rankings across 9 categories.
//
//    Categories available:
//
//    General
//            World
//    Nation
//            Business
//    Technology
//            Entertainment
//    Sports
//            Science
//    Health



    public NoticiaRespostaDTO getNoticiasPorCategoriasTopHeadlines(String categoria){

        String url = GNEWS_URL + "/top-headlines" + "?apikey=" + apiKey + "&category=" + categoria;
        try {
            return restTemplate.getForObject(url, NoticiaRespostaDTO.class);
        }catch (Exception e){
            System.err.println("erro ao acessar as noticias" + e.getMessage());
            return null;
        }
    }

    public NoticiaRespostaDTO getNoticiasPorTermoDeCategoria(String termoDeBusca){
        String url = GNEWS_URL + "/search" + "?apikey=" + apiKey + "&q=" + termoDeBusca;
        try{
            return restTemplate.getForObject(url, NoticiaRespostaDTO.class);
        }catch (Exception e) {
            System.err.println("erro ao acessar as noticias" + e.getMessage());
            return null;
        }
    }
}

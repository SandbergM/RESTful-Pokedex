package com.example.Pokedex.services;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

import com.example.Pokedex.dto.PokemonDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@ConfigurationProperties(value = "example.pokemon", ignoreUnknownFields = false)
public class PokemonConsumerService {

    private final RestTemplate restTemplate;
    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonConsumerService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public PokemonDto searchByName(String name){
        var urlWithQuery = URL + name;
        return restTemplate.getForObject(urlWithQuery, PokemonDto.class);
    }
}

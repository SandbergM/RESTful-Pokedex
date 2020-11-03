package com.example.Pokedex.services;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import com.example.Pokedex.dto.ItemDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConfigurationProperties(value = "example.item", ignoreUnknownFields = false)

public class ItemConsumerService {

    private final RestTemplate restTemplate;
    private static final String URL = "https://pokeapi.co/api/v2/item/";

    public ItemConsumerService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public ItemDto searchByName(String name){
        var urlWithQuery = URL + name;
        return restTemplate.getForObject(urlWithQuery, ItemDto.class);
    }
}

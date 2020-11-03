package com.example.Pokedex.services;

import com.example.Pokedex.dto.PokemonDto;
import com.example.Pokedex.entities.Pokemon;
import com.example.Pokedex.entities.PokemonAbility;
import com.example.Pokedex.entities.PokemonType;
import com.example.Pokedex.mappers.PokemonMapper;
import com.example.Pokedex.repositories.PokemonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

@Service
public class PokemonService {

    @Autowired
    private PokemonRepo pokemonRepo;
    @Autowired
    private PokemonMapper pokemonMapper;
    private final RestTemplate restTemplate;
    private final PokemonConsumerService pokemonConsumerService;

    public PokemonService(RestTemplateBuilder restTemplateBuilder, PokemonConsumerService pokemonConsumerService){
        this.restTemplate = restTemplateBuilder.build();
        this.pokemonConsumerService = pokemonConsumerService;
    }

    @Cacheable(value = "pokemonCache")
    public List<Pokemon> pokemonSearch(String name, String type, int weight, int height, String ability, int page){

        var result = pokemonRepo.pokemonDatabaseCriteriaSearch(name, type, weight, height, ability, page);

        if(page > 1 && result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, no matches found");
        }

        var partialNameSearchList = this.fetchPokemonNames();

        if( result.isEmpty() || partialNameSearchList.size() > 0){
            for(String pokemonName : partialNameSearchList){
                if(pokemonName.contains(name)){
                    var savedPokemon = pokemonRepo.findByName(pokemonName);
                    if(savedPokemon.isEmpty()){
                        var pokemonDto = pokemonConsumerService.searchByName(pokemonName);
                        var pokemon = this.assemblePokemon(pokemonDto);
                        if( this.valueCheck(pokemon, type, weight, height, ability) ){
                            result.add(pokemon);
                        }
                        this.save(pokemon);
                    }
                }
            }
        }

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, no matches found");
        }
        return result;
    }

    @CacheEvict(value = "pokemonCache", key="#result.id", allEntries = true)
    public Pokemon save(Pokemon pokemon){
        return pokemonRepo.save(pokemon);
    }

    @CachePut(value = "pokemonCache", key = "#id")
    public void update(String id, Pokemon pokemon){
        if(pokemonRepo.findById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find pokemon with id : " + id);
        }
        pokemonRepo.save(pokemon);
    }

    @CacheEvict(value = "pokemonCache", allEntries = true)
    public void delete(String id){
        if(pokemonRepo.findById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find pokemon with id : " + id);
        }
        pokemonRepo.deleteById(id);
    }

    @Cacheable( value = "pokemonApiNameCache", key="#name")
    public List<String> fetchPokemonNames(){

        String URL = "https://pokeapi.co/api/v2/pokemon/?limit=2000";
        List<String> result = new ArrayList<>();
        List<Pokemon> dbPokemons = pokemonRepo.findAll();
        List<String> namesInDatabase = new ArrayList<>();

        var resultAsString = restTemplate.getForObject(URL, LinkedHashMap.class);
        assert resultAsString != null;
        ArrayList<?> resultAsArray = (ArrayList<?>) resultAsString.get("results");

        for(Pokemon pokemon : dbPokemons){
            namesInDatabase.add(pokemon.getName());
        }

        for (Object obj : resultAsArray) {
            var x = obj.toString();
            var name = x.substring(x.indexOf("name=") + 5, x.indexOf(","));
            if(!namesInDatabase.contains(name)){
                result.add(name);
            }
        }
        return result;
    }

    // Hacky way of getting a fake "multiple param-search" with the pokemon-api.
    public boolean valueCheck(Pokemon pokemon, String type,  int weight, int height, String ability ){
        boolean hasCorrectType = type.equals("");
        boolean hasCorrectWeight = pokemon.getWeight() == weight || weight == -1;
        boolean hasCorrectHeight = pokemon.getHeight() == height || height == -1;
        boolean hasCorrectAbility = ability.equals("");

        for(PokemonAbility a : pokemon.getAbilities()){
            if (a.getAbility().getName().equalsIgnoreCase(ability)) {
                hasCorrectAbility = true;
                break;
            }
        }

        for(PokemonType a : pokemon.getTypes()){
            if (a.getType().getName().equalsIgnoreCase(type)) {
                hasCorrectType = true;
                break;
            }
        }
        return hasCorrectWeight && hasCorrectHeight && hasCorrectType && hasCorrectAbility;
    }

    public Pokemon assemblePokemon(PokemonDto pokemonDto){
        return new Pokemon(
                pokemonDto.getName(),
                pokemonDto.getAbilities(),
                pokemonDto.getBase_experience(),
                pokemonDto.getForms(),
                pokemonDto.getGame_indices(),
                pokemonDto.getHeight(),
                pokemonDto.getHeld_items(),
                pokemonDto.getId(),
                pokemonDto.getIs_default(),
                pokemonDto.getLocation_area_encounters(),
                pokemonDto.getMoves(),
                pokemonDto.getTypes(),
                pokemonDto.getOrder(),
                pokemonDto.getWeight()
        );
    }
}

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

    public PokemonService(RestTemplateBuilder restTemplateBuilder, PokemonConsumerService pokemonConsumerService) {
        this.restTemplate = restTemplateBuilder.build();
        this.pokemonConsumerService = pokemonConsumerService;
    }

    @Cacheable(value = "pokemonCache")
    public List<Pokemon> pokemonSearch(String name, String type, int weight, int height, String ability, int page, Boolean firstSearch) {

        var result = pokemonRepo.pokemonDatabaseCriteriaSearch(name, type, weight, height, ability, page)
                .orElse(new ArrayList<>());
        // If (this is the first search or result doesn't have any pokemon's) and ( the result has less than 50 pokemon's )
        // if you only check if result is empty or not you could loose x amount of pokemon's in every search
        // if i get at least 1 hit from the database, the result is a bit more traffic towards PokeApi, however
        // the search results will be A LOT more accurate and return a lot more data
        // I only fetch a list with all of the names of the pokemon's that are provided by PokeApi, but i only
        // fetch the entire object if it matches a users search criteras
        if ((firstSearch || result.isEmpty()) && result.size() < 50) {
            var names = this.fetchPokemonNames();
            for (String pokemonName : names) {
                if (pokemonName.contains(name) && !pokemonRepo.findOneWithName(pokemonName)) {
                    var pokemonDto = pokemonConsumerService.searchByName(pokemonName);
                    Pokemon pokemon = pokemonMapper.map(pokemonDto);
                    result.add(this.save(pokemon));
                }
            }
            this.pokemonSearch(name, type, weight, height, ability, page, false);
        }

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, no matches found");
        }

        return result;
    }

    @CacheEvict(value = "pokemonCache", key = "#result.id", allEntries = true)
    public Pokemon save(Pokemon pokemon) {
        return pokemonRepo.save(pokemon).orElse(null);
    }

    @CachePut(value = "pokemonCache", key = "#id")
    public void update(String id, Pokemon pokemon) {
        if (pokemonRepo.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, couldn't find pokemon with that id");
        }
        pokemonRepo.save(pokemon);
    }

    @CacheEvict(value = "pokemonCache", allEntries = true)
    public void delete(String id) {
        if (pokemonRepo.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, couldn't find pokemon with that id");
        }
        pokemonRepo.deleteById(id);
    }

    // PokeAPI only support id / name(full) search, when a user does a search without a name this
    // method checks any of the fetched pokemon's matches the criteria(weight, height, type, ability)
    public boolean valueCheck(Pokemon pokemon, String type, int weight, int height, String ability) {
        boolean hasCorrectType = type.equals("");
        boolean hasCorrectWeight = pokemon.getWeight() == weight || weight == -1;
        boolean hasCorrectHeight = pokemon.getWeight() == height || height == -1;
        boolean hasCorrectAbility = ability.equals("");

        for (PokemonAbility a : pokemon.getAbilities()) {
            if (a.getAbility().getName().equalsIgnoreCase(ability)) {
                hasCorrectAbility = true;
                break;
            }
        }

        for (PokemonType a : pokemon.getTypes()) {
            if (a.getType().getName().equalsIgnoreCase(type)) {
                hasCorrectType = true;
                break;
            }
        }
        return hasCorrectWeight && hasCorrectHeight && hasCorrectType && hasCorrectAbility;
    }

    // Fetch all of the pokemon-names from the API and store it in cache to lower the traffic to PokeAPI
    // but only saves the names of the pokemon's that doesn't already exist in my own database
    // this list is used in checkForUpdatesInPokeApi() to check a users search needs any of the pokemon's
    // from PokeAPI that i've not already saved in my database.
    @Cacheable(value = "pokemonApiNameCache", key = "#name")
    public List<String> fetchPokemonNames() {
        String URL = "https://pokeapi.co/api/v2/pokemon/?limit=2000";
        List<String> result = new ArrayList<>();
        var resultAsString = restTemplate.getForObject(URL, LinkedHashMap.class);
        assert resultAsString != null;
        ArrayList<?> resultAsArray = (ArrayList<?>) resultAsString.get("results");

        for (Object obj : resultAsArray) {
            var x = obj.toString();
            var name = x.substring(x.indexOf("name=") + 5, x.indexOf(","));
            result.add(name);
        }
        return result;
    }
}
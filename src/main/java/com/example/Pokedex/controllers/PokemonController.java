package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.Pokemon;
import com.example.Pokedex.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

@RestController
@RequestMapping("/api/v1/pokemon/")

public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping()
    public ResponseEntity<List<Pokemon>> pokemonSearch(
            @RequestParam( value="name", required = false, defaultValue = "" ) String name,
            @RequestParam( value="type", required = false, defaultValue = "" ) String type,
            @RequestParam( value="weight", required = false, defaultValue = "-1" ) Integer weight,
            @RequestParam( value="height", required = false, defaultValue = "-1" ) Integer height,
            @RequestParam( value="ability", required = false, defaultValue = "" ) String ability,
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page
    ){
        var pokemon = pokemonService.pokemonSearch(name, type, weight, height, ability, page);
        return ResponseEntity.ok(pokemon);
    }

    @PostMapping()
    public ResponseEntity<Pokemon> savePokemon(
            @RequestBody Pokemon pokemon
    ){
        var savedPokemon = pokemonService.save(pokemon);
        var uri = URI.create("/api/v1/pokemon?id=" + savedPokemon.getId());
        return ResponseEntity.created(uri).body(savedPokemon);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePokemon(
            @RequestBody Pokemon pokemon,
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.update(id, pokemon);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePokemon(
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.delete(id);
    }
}

package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.Pokemon;
import com.example.Pokedex.services.PokemonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

@RestController
@RequestMapping("/api/v1/pokemon")
@Api( tags = "Pokemon Controller" )
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping( produces = "application/json" )
    @ApiOperation( value = "Search for a pokemon" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Ok" ),
            @ApiResponse( code = 404, message = "Not found" )
    } )
    public ResponseEntity<List<Pokemon>> pokemonSearch(
            @ApiParam( value = "Used to search for a pokemon with full or partial name", required = false)
            @RequestParam( value="name", required = false, defaultValue = "") String name,
            @ApiParam( value = "Used to search for a pokemon belonging to a specific type", required = false)
            @RequestParam( value="type", required = false, defaultValue = "" ) String type,
            @ApiParam( value = "Used to search for a pokemon based on weight", required = false)
            @RequestParam( value="weight", required = false, defaultValue = "-1") Integer weight,
            @ApiParam( value = "Used to search for a pokemon based on height", required = false)
            @RequestParam( value="height", required = false, defaultValue = "-1" ) Integer height,
            @ApiParam( value = "Used to search for a pokemon with a specific ability", required = false)
            @RequestParam( value="ability", required = false, defaultValue = "" ) String ability,
            @ApiParam( value = "The page of results, 50 per page", required = false)
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page
    ){
        var pokemon = pokemonService.pokemonSearch(name, type, weight, height, ability, page, true );
        return ResponseEntity.ok(pokemon);
    }

    @PostMapping( consumes = "application/json", produces = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ApiOperation( value = "Save new pokemon to the database" )
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Created" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
    })
    public ResponseEntity<Pokemon> savePokemon(
            @RequestBody Pokemon pokemon
    ){
        var savedPokemon = pokemonService.save(pokemon);
        var uri = URI.create("/api/v1/pokemon?id=" + savedPokemon.getId());
        return ResponseEntity.created(uri).body(savedPokemon);
    }

    @PutMapping( consumes = "application/json", produces = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation( value = "Update a pokemon in the database" )
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "No content" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" )
    })
    public void updatePokemon(
            @RequestBody Pokemon pokemon,
            @ApiParam( value = "The MongoDb _id of the pokemon you want to update", required = true)
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.update(id, pokemon);
    }


    @DeleteMapping()
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation( value = "Remove a pokemon in the database" )
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "No content" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" )
    })
    public void deletePokemon(
            @ApiParam( value = "The MongoDb _id of the pokemon you want to delete", required = true)
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.delete(id);
    }
}

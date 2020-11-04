package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.Pokemon;
import com.example.Pokedex.services.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping( produces = "application/json" )
    @Secured({"ROLE_ADMIN"})
    @Operation(
            summary = "Search for a pokemon",
            description = "Search for an item, if no params are provided will preform a findAll",
            tags = "pokemon"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content( array = @ArraySchema( schema = @Schema(implementation = Pokemon.class) ) ) ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sorry, no matches found",
                    content = @Content )
    } )
    public ResponseEntity<List<Pokemon>> pokemonSearch(
            @RequestParam( value="name", required = false, defaultValue = "" ) String name,
            @RequestParam( value="type", required = false, defaultValue = "" ) String type,
            @RequestParam( value="weight", required = false, defaultValue = "-1" ) Integer weight,
            @RequestParam( value="height", required = false, defaultValue = "-1" ) Integer height,
            @RequestParam( value="ability", required = false, defaultValue = "" ) String ability,
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page
    ){
        var pokemon = pokemonService.pokemonSearch(name, type, weight, height, ability, page, true );
        return ResponseEntity.ok(pokemon);
    }

    @PostMapping( consumes = "application/json", produces = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @Operation(
            summary = "Save new pokemon to the database",
            description = "Save a new pokemon to the database",
            tags = "pokemon"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content( schema = @Schema(implementation = Pokemon.class) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content),
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
    @Operation(
            summary = "Update a pokemon in the database",
            description = "Update a pokemon in the database with a specific _id",
            tags = "pokemon"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful operation",
                    content = @Content(  schema = @Schema(implementation = Pokemon.class) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Couldn't find pokemon with that id",
                    content = @Content )
    })
    public void updatePokemon(
            @RequestBody Pokemon pokemon,
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.update(id, pokemon);
    }


    @DeleteMapping( consumes = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Remove a pokemon in the database",
            description = "Remove a pokemon from the database with a specific _id",
            tags = "pokemon"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful operation",
                    content = @Content(  schema = @Schema(implementation = Pokemon.class) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Couldn't find pokemon with that id",
                    content = @Content)
    })
    public void deletePokemon(
            @RequestParam(value = "id", required = true) String id
    ){
        pokemonService.delete(id);
    }
}

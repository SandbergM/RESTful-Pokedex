package com.example.Pokedex.controllers;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import com.example.Pokedex.entities.Item;
import com.example.Pokedex.services.ItemService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")

public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping( produces = "application/json" )
    @Operation(
            summary = "Search for an item",
            description = "Search for an item, if no params are provided will preform a findAll",
            tags = "item"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(  array = @ArraySchema( schema = @Schema(implementation = Item.class) ) ) ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sorry, no matches found",
                    content = @Content )
    } )
    public ResponseEntity<List<Item>> itemSearch(
            @RequestParam( value="name", required = false, defaultValue = "" ) String name,
            @RequestParam( value="minCost", required = false, defaultValue = "-2147483648" ) Integer minCost,
            @RequestParam( value="maxCost", required = false, defaultValue = "2147483647" ) Integer maxCost,
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page
    ){
        var item = itemService.itemSearch( name, minCost, maxCost, page,true );
        return ResponseEntity.ok(item);
    }

    @Operation(
            summary = "Save new item to the database",
            description = "Save a new item to the database",
            tags = "item"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content( schema = @Schema(implementation = Item.class) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Bad credentials",
                    content = @Content )
    })
    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    public ResponseEntity<Item> saveItem(
            @Validated @RequestBody Item item
    ){
        var savedItem = itemService.save(item);
        var uri = URI.create("/api/v1/item?id=" + savedItem.getId());
        return ResponseEntity.created(uri).body(savedItem);
    }

    @Operation(
            summary = "Update an item in the database",
            description = "Update an item in the database with a specific _id",
            tags = "item"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful operation",
                    content = @Content(  schema = @Schema(implementation = Item.class) ) ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Couldn't find an item with that id",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Bad credentials",
                    content = @Content )
    })
    @PutMapping( consumes = "application/json", produces = "application/json" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(
            @RequestBody Item item,
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.update(id, item);
    }

    @Operation(
            summary = "Remove an item from the database",
            description = "Remove an item from the database with a specific _id",
            tags = "item"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Bad credentials",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Couldn't find pokemon with that id",
                    content = @Content)
    })
    @DeleteMapping( consumes = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.delete(id);
    }
}

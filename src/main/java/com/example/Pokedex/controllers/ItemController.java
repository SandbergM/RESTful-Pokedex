package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.Item;
import com.example.Pokedex.services.ItemService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

@RestController
@RequestMapping("/api/v1/item")
@Api( tags = "Item Controller" )
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping( produces = "application/json" )
    @ApiOperation( value = "Search for an item" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Ok" ),
            @ApiResponse( code = 404, message = "Not found" )
    } )
    public ResponseEntity<List<Item>> itemSearch(
            @ApiParam( value = "Used to search for an item with full or partial name", required = false)
            @RequestParam( value="name", required = false, defaultValue = "" ) String name,
            @ApiParam( value = "Used to search for an item with minimum price", required = false)
            @RequestParam( value="minCost", required = false, defaultValue = "-2147483648" ) Integer minCost,
            @ApiParam( value = "Used to search for an item with maximum price", required = false)
            @RequestParam( value="maxCost", required = false, defaultValue = "2147483647" ) Integer maxCost,
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page

    ){
        var item = itemService.itemSearch( name, minCost, maxCost, page,true );
        return ResponseEntity.ok(item);
    }

    @ApiOperation(value = "Save new item to the database")
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Created" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
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

    @ApiOperation( value = "Update an item in the database" )
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "No content" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" )
    })
    @PutMapping( consumes = "application/json", produces = "application/json" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(
            @RequestBody Item item,
            @ApiParam( value = "The MongoDb _id of the item you want to update", required = true)
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.update(id, item);
    }

    @ApiOperation( value = "Remove an item from the database" )
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "No content" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" )
    })
    @DeleteMapping( consumes = "application/json" )
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @ApiParam( value = "The MongoDb _id of the item you want to delete", required = true)
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.delete(id);
    }
}

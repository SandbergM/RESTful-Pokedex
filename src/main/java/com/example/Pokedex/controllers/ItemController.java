package com.example.Pokedex.controllers;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import com.example.Pokedex.entities.Item;
import com.example.Pokedex.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")

public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<Item>> itemSearch(
            @RequestParam( value="name", required = false, defaultValue = "" ) String name,
            @RequestParam( value="minCost", required = false, defaultValue = "-2147483648" ) Integer minCost,
            @RequestParam( value="maxCost", required = false, defaultValue = "2147483647" ) Integer maxCost,
            @RequestParam( value="page", required = false, defaultValue = "1" ) Integer page
    ){
        var item = itemService.itemSearch(name, minCost, maxCost, page);
        return ResponseEntity.ok(item);
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    public ResponseEntity<Item> saveItem(
            @RequestBody Item item
    ){
        var savedItem = itemService.save(item);
        var uri = URI.create("/api/v1/item?id=" + savedItem.getId());
        return ResponseEntity.created(uri).body(savedItem);
    }

    @PutMapping()
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(
            @RequestBody Item item,
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.update(id, item);
    }

    @DeleteMapping()
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @RequestParam(value = "id", required = true) String id
    ){
        itemService.delete(id);
    }
}

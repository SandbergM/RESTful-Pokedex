package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.Item;
import com.example.Pokedex.entities.User;
import com.example.Pokedex.services.UserService;
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

import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation( summary = "Search for a user in the database with a specific username", tags = "user")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(  array = @ArraySchema( schema = @Schema(implementation = User.class) ) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content )
    } )
    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<User>> findByUsername(@PathVariable(required = false) String username){
        var users = userService.findAll(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation( summary = "Register / Add a new account to the database", tags = "user" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(  array = @ArraySchema( schema = @Schema(implementation = User.class) ) ) ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict, already in use",
                    content = @Content )
    } )
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> createUser(@Validated @RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation( summary = "Update / Put account in the database with a specific id", tags = "user" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful operation",
                    content = @Content(  array = @ArraySchema( schema = @Schema(implementation = User.class) ) ) ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict, already in use",
                    content = @Content )
    } )
    @PutMapping("{id}")
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id,@Validated @RequestBody User user){
        userService.updateUser(id, user);
    }

    @Operation( summary = "Delete / Remove account from the database with a specific id", tags = "user" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful operation",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content )
    } )
    @DeleteMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }
}

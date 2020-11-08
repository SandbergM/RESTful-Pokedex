package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.User;
import com.example.Pokedex.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/api/v1/users")
@Api( tags = "User Controller" )
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation( value = "Search for a user in the database with a specific username" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Successful operation" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden"  ),
            @ApiResponse( code = 404, message = "Not found" )
    } )
    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<User>> userSearchUsername(
            @RequestParam( value = "username" ,required = false, defaultValue = "") String username
    ){
        var users = userService.userSearch(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Search for a user in the database with a specific id")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Successful operation" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden"  ),
            @ApiResponse( code = 404, message = "Not found" )
    } )
    @GetMapping( "/{id}" )
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> userSearchId(
            @PathVariable( value = "id" ) String id
    ){
        var user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation( value = "Register / Add a new account to the database")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Successful operation" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 404, message = "Not found" ),
            @ApiResponse( code = 409, message = "Conflict, already in use" )
    } )
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> createUser(@Validated @RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @ApiOperation( value = "Update / Put account in the database with a specific id")
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Successful operation" ),
            @ApiResponse( code = 400, message = "Bad Request" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" ),
            @ApiResponse( code = 409, message = "Conflict, already in use" )
    } )
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @PathVariable( value = "id") String id,
            @Validated @RequestBody User user
    ){
        userService.updateUser(id, user);
    }

    @ApiOperation( value = "Delete / Remove account from the database with a specific id")
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "Successful operation" ),
            @ApiResponse( code = 401, message = "Bad credentials" ),
            @ApiResponse( code = 403, message = "Forbidden" ),
            @ApiResponse( code = 404, message = "Not found" )
    } )
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable( value = "id" ) String id
    ){
        userService.deleteUser(id);
    }
}

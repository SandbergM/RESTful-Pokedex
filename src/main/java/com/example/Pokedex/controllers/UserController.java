package com.example.Pokedex.controllers;

import com.example.Pokedex.entities.User;
import com.example.Pokedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<User>> findByUsername(@PathVariable(required = false) String username){
        var users = userService.findAll(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id,@Validated @RequestBody User user){
        userService.updateUser(id, user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }
}

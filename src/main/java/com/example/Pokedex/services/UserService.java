package com.example.Pokedex.services;

import com.example.Pokedex.entities.User;
import com.example.Pokedex.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user){
        if(StringUtils.isEmpty(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Password required");
        }
        if(userRepo.findByUsername(user.getUsername()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
        }

        if(userRepo.findUserByEmail(user.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void updateUser(String id, User user) {
        if(!userRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        if(!username.equals(user.getUsername())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        userRepo.save(user);
    }

    public void deleteUser(String id) {
        if(!userRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        userRepo.deleteById(id);
    }

    public List<User> findAll(String username) {
        if(username == null){
            return userRepo.findAll();
        }
        var user = userRepo.findByUsername(username);
        return List.of(user);
    }

    public User findByUsername(String username) {
        var user = userRepo.findByUsername(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return user;
    }
}

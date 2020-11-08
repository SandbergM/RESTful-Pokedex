package com.example.Pokedex.services;

import com.example.Pokedex.entities.User;
import com.example.Pokedex.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        this.passwordCheck(user.getPassword());
        this.usernameAvailabilityCheck(user.getUsername());
        this.emailAvailabilityCheck(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void updateUser(String id, User updatedUserProfile) {

        var loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User oldUserProfile = userRepo.findById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id)));

        if( !oldUserProfile.getUsername().equals(loggedInUser.getName()) ) {
            if( !isAdmin ){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }
        }

        // Checks to see if there's a request to change username
        // If so, checks if the new username is available
        if(!oldUserProfile.getUsername().equals(updatedUserProfile.getUsername())){
            this.usernameAvailabilityCheck(updatedUserProfile.getUsername());
        }
        // Checks to see if there's a request to change email
        // If so, checks if the new email is free available
        if(!oldUserProfile.getEmail().equals(updatedUserProfile.getEmail())){
            this.emailAvailabilityCheck(updatedUserProfile.getEmail());
        }

        // Only admins are allowed to change the roles of an account
        if (!isAdmin) { updatedUserProfile.setRoles( oldUserProfile.getRoles()); }

        updatedUserProfile.setPassword(passwordEncoder.encode(updatedUserProfile.getPassword()));
        updatedUserProfile.setId(id);
        userRepo.save(updatedUserProfile);
    }

    public void deleteUser(String id) {
        if(!userRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id));
        }
        userRepo.deleteById(id);
    }

    public List<User> userSearch(String username) {

        if(!username.equals("")){
            User user = this.findByUsername(username);
            return (List<User>) user;
        }

        var users = userRepo.findAll();
        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        return users;
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", username)));
    }

    public User findById(String id){
        return userRepo.findById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id)));
    }

    private void usernameAvailabilityCheck(String username){
        if(userRepo.findByUsername(username).orElse( null ) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Conflict, already in use %s", username));
        }
    }

    private void emailAvailabilityCheck(String email){
        if(userRepo.findUserByEmail(email).orElse( null ) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Conflict, already in use %s", email));
        }
    }

    private void passwordCheck(String password){
        if(password == null){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "A password is required");
        };
    }
}

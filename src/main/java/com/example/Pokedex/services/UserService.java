package com.example.Pokedex.services;

import com.example.Pokedex.entities.User;
import com.example.Pokedex.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public void updateUser(String id, User user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUsername = ((UserDetails)principal).getUsername();
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        User oldUserProfile = userRepo.findById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id)));

        for(var role : roles){
            System.out.println(role.getAuthority());
            if(role.getAuthority().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if( !oldUserProfile.getUsername().equals(loggedInUsername) ){
            if( !isAdmin ){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }
        }

        if(!oldUserProfile.getUsername().equals(user.getUsername())){
            this.usernameAvailabilityCheck(user.getUsername());
        }
        if(!oldUserProfile.getEmail().equals(user.getEmail())){
            this.emailAvailabilityCheck(user.getEmail());
        }

        if (!isAdmin) { user.setRoles( oldUserProfile.getRoles()); }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        userRepo.save(user);
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
    // Only used in other methods that handle the status codes them self
    public User findByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", username)));
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

    private void idCheck(String id){
        if(userRepo.findById(id).orElse( null ) != null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id));
        }
    }

    private void passwordCheck(String password){
        if(password == null){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "A password is required");
        };
    }
}

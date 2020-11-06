package com.example.Pokedex.repositories;

import com.example.Pokedex.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);
}

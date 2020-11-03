package com.example.Pokedex.repositories;

import com.example.Pokedex.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findUserByEmail(String email);
}

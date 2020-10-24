package com.example.Pokedex.repositories;

import com.example.Pokedex.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);
}

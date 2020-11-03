package com.example.Pokedex.repositories;

import com.example.Pokedex.entities.Pokemon;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

@Repository
public class PokemonRepo {

    private final MongoTemplate mongoTemplate;
    private final int PAGE_LIMIT = 50;

    public PokemonRepo(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<Pokemon> pokemonDatabaseCriteriaSearch(String name, String type, int weight, int height, String ability, int page){
        Query query = new Query();

        if(weight != -1){query.addCriteria(Criteria.where("weight").is(weight));}
        if(height != -1){query.addCriteria(Criteria.where("height").is(height));}
        if(!ability.equals("")){ query.addCriteria(Criteria.where("abilities.ability.name").in(ability)); }
        if(!type.equals("")){ query.addCriteria(Criteria.where("types.type.name").in(type)); }

        query.addCriteria(Criteria.where("name").regex(name))
                .with( Sort.by( "name" ) )
                .limit( PAGE_LIMIT )
                .skip( ( page - 1 ) * PAGE_LIMIT );

        return mongoTemplate.find(query, Pokemon.class);
    }

    public Pokemon save(Pokemon pokemon){
        return mongoTemplate.save(pokemon);
    }

    public List<Pokemon> findByName(String name){
        Query query = new Query().addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Pokemon.class);
    }

    public Pokemon findById(String id){
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Pokemon.class);
    }

    public void deleteById(String id){
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.findAndRemove(query, Pokemon.class);
    }
}

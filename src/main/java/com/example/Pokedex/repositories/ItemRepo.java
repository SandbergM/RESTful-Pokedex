package com.example.Pokedex.repositories;

import com.example.Pokedex.entities.Item;
import com.example.Pokedex.entities.Pokemon;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepo {

    private final MongoTemplate mongoTemplate;
    private final int PAGE_LIMIT = 50;

    public ItemRepo(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<Item> itemDatabaseCriteriaSearch(String name, int minCost, int maxCost, int page){
        Query query = new Query();

        if(minCost != Integer.MIN_VALUE || maxCost != Integer.MAX_VALUE){
            query.addCriteria(Criteria.where("cost").gte(minCost).lte(maxCost));
        }

        query.addCriteria(Criteria.where("name").regex(name))
                .with( Sort.by( "name" ) )
                .limit( PAGE_LIMIT )
                .skip( ( page - 1 ) * PAGE_LIMIT );

        return mongoTemplate.find(query, Item.class);
    }

    public List<Item> findAll(){
        return mongoTemplate.findAll(Item.class);
    }

    public Item save(Item item){
        return mongoTemplate.save(item);
    }

    public List<Item> findByName(String name){
        Query query = new Query().addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Item.class);
    }

    public Item findById(String id){
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Item.class);
    }

    public void deleteById(String id){
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.findAndRemove(query, Item.class);
    }
}

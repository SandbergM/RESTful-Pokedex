package com.example.Pokedex.services;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import com.example.Pokedex.dto.ItemDto;
import com.example.Pokedex.entities.Item;
import com.example.Pokedex.mappers.ItemMapper;
import com.example.Pokedex.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemMapper itemMapper;
    private final RestTemplate restTemplate;
    private final ItemConsumerService itemConsumerService;

    public ItemService(RestTemplateBuilder restTemplateBuilder, ItemConsumerService itemConsumerService){
        this.restTemplate = restTemplateBuilder.build();
        this.itemConsumerService = itemConsumerService;
    }

    @Cacheable(value = "itemCache")
    public List<Item> itemSearch(String name, int minCost, int maxCost, int page){

        var result = itemRepo.itemDatabaseCriteriaSearch(name, minCost, maxCost, page);

        var partialNameSearchList = this.fetchItemNames();

        System.out.println(partialNameSearchList.size());

        if( result.isEmpty() || partialNameSearchList.size() > 0){
            for(String itemName : partialNameSearchList){
                if(itemName.contains(name)){
                    var savedItem = itemRepo.findByName(itemName);
                    if(savedItem.isEmpty()){
                        var itemDto = itemConsumerService.searchByName(itemName);
                        var item = this.assembleItem(itemDto);
                        if(this.valueCheck(item, minCost, maxCost)){
                            result.add(item);
                        }
                        System.out.println("Item added to DB");
                        this.save(item);
                    }
                }
            }
        }

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, no matches found");
        }
        return result;
    }

    @CacheEvict(value = "itemCache", key="#result.id", allEntries = true)
    public Item save(Item item){
        return itemRepo.save(item);
    }

    @CachePut(value = "itemCache", key = "#id")
    public void update(String id, Item item){
        if(itemRepo.findById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id : " + id);
        }
        itemRepo.save(item);
    }

    @CacheEvict(value = "itemCache", allEntries = true)
    public void delete(String id){
        if(itemRepo.findById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id : " + id);
        }
        itemRepo.deleteById(id);
    }

    @Cacheable( value = "pokemonApiItemCache", key="#name")
    public List<String> fetchItemNames(){

        List<String> result = new ArrayList<>();
        List<Item> dbItems = itemRepo.findAll();
        List<String> namesInDatabase = new ArrayList<>();

        String URL = "https://pokeapi.co/api/v2/item/?limit=2000";
        var resultAsString = restTemplate.getForObject(URL, LinkedHashMap.class);
        assert resultAsString != null;
        List<?> resultAsArray = (List<?>) resultAsString.get("results");

        for(Item item : dbItems){
            namesInDatabase.add(item.getName());
        }

        for (Object obj : resultAsArray) {
            var x = obj.toString();
            var name = x.substring(x.indexOf("name=") + 5, x.indexOf(","));
           if(!namesInDatabase.contains(name)){
               System.out.println("added");
                result.add(name);
            }
        }
        return result;
    }

    // Hacky way of getting a fake "multiple param-search" with the pokemon-api.
    public boolean valueCheck(Item item, int minCost, int maxCost ){
        boolean isNotToCheap = minCost == Integer.MIN_VALUE || minCost <= item.getCost();
        boolean isNotToExpensive = maxCost == Integer.MAX_VALUE || maxCost >= item.getCost();
        return isNotToCheap && isNotToExpensive;
    }

    public Item assembleItem(ItemDto itemDto){
        return new Item(
                itemDto.getId(),
                itemDto.getAttributes(),
                itemDto.getCategory(),
                itemDto.getCost(),
                itemDto.getName()
        );
    }
}
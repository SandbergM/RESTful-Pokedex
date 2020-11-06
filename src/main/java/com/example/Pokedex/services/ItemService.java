package com.example.Pokedex.services;

import com.example.Pokedex.dto.ItemDto;
import com.example.Pokedex.entities.Item;
import com.example.Pokedex.entities.Pokemon;
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

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

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
    public List<Item> itemSearch( String name, int minCost, int maxCost, int page, boolean firstSearch ){

        List<Item> result = itemRepo.itemDatabaseCriteriaSearch(name, minCost, maxCost, page).orElse(new ArrayList<>());

        // If this is the first search and the result has less than 50 item's
        // if you only check if result is empty or not you could loose x amount of item's in every search
        // the search results will be A LOT more accurate and return a lot more data
        // The downside is that some requests might take a bit more time
        if ( result.size() < 50 ) {
            var names = this.fetchItemNames();
            for (String itemName : names) {
                if (itemName.contains(name)) {
                    Item existingItem = itemRepo.findOneWithName(itemName).orElse( null );
                    if(existingItem == null){
                        var itemDto = itemConsumerService.searchByName(itemName);
                        Item item = itemMapper.map(itemDto);
                        this.save(item);
                    }
                }
            }
            if(firstSearch){
                this.itemSearch(name, minCost, maxCost, page,false);
            }
        }

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return result;
    }

    @CacheEvict(value = "itemCache", key="#result.id", allEntries = true)
    public Item save(Item item){
        return itemRepo.save(item).orElse(null );
    }

    @CachePut(value = "itemCache", key = "#id")
    public void update(String id, Item item){
        if(itemRepo.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id));
        }
        itemRepo.save(item);
    }

    @CacheEvict(value = "itemCache", allEntries = true)
    public void delete(String id){
        if(itemRepo.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found %s", id));
        }
        itemRepo.deleteById(id);
    }

    @Cacheable( value = "pokemonApiItemCache", key="#name")
    public List<String> fetchItemNames(){
        String URL = "https://pokeapi.co/api/v2/item/?limit=2000";
        List<String> result = new ArrayList<>();

        var resultAsString = restTemplate.getForObject(URL, LinkedHashMap.class);
        assert resultAsString != null;
        List<?> resultAsArray = (List<?>) resultAsString.get("results");

        for (Object obj : resultAsArray) {
            var x = obj.toString();
            var name = x.substring(x.indexOf("name=") + 5, x.indexOf(","));
            result.add(name);
        }
        return result;
    }

    // Hacky way of getting a fake "multiple param-search" with the pokemon-api.
    public boolean valueCheck(Item item, int minCost, int maxCost ){
        boolean isNotToCheap = minCost == Integer.MIN_VALUE || minCost <= item.getCost();
        boolean isNotToExpensive = maxCost == Integer.MAX_VALUE || maxCost >= item.getCost();
        return isNotToCheap && isNotToExpensive;
    }

    // Helper to make partial-name search work with the PokeAPI, since they don't
    // support it them self, if a match is found, i save it to my own database.
    public void checkForUpdatesInPokeApi(List<String> names, String nameSearched){
        for(String itemName : names){
            if(itemName.contains(nameSearched)){
                var itemDto = itemConsumerService.searchByName(itemName);
                var item = this.assembleItem(itemDto);
                this.save(item);
            }
        }
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
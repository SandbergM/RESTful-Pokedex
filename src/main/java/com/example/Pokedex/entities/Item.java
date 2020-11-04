package com.example.Pokedex.entities;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private static final long serialVersionUID = 7270035226459372508L;

    @Id
    @Schema(description = "The _id used by MongoDb", example = "5fa265933abf5a24d97169d7")
    private String id;
    @Schema(description = "The id from PokeApi", example = "10034")
    private Integer poke_api_id;
    private List<NameAndUrl> attributes;
    private NameAndUrl category;
    @Schema(description = "The cost of an item", example = "10000")
    private Integer cost;
    @Schema(description = "The name of an item", example = "master-ball")
    private String name;

    public Item() {
    }

    public Item(Integer poke_api_id, List<NameAndUrl> attributes, NameAndUrl category, Integer cost, String name) {
        this.poke_api_id = poke_api_id;
        this.attributes = attributes;
        this.category = category;
        this.cost = cost;
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoke_api_id() {
        return poke_api_id;
    }

    public void setPoke_api_id(Integer poke_api_id) {
        this.poke_api_id = poke_api_id;
    }

    public List<NameAndUrl> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<NameAndUrl> attributes) {
        this.attributes = attributes;
    }

    public NameAndUrl getCategory() {
        return category;
    }

    public void setCategory(NameAndUrl category) {
        this.category = category;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

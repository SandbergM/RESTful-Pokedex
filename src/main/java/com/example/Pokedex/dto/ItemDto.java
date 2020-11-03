package com.example.Pokedex.dto;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import com.example.Pokedex.entities.GameIndicy;
import com.example.Pokedex.entities.NameAndUrl;

import java.util.List;

public class ItemDto {

    private List<NameAndUrl> attributes;
    private NameAndUrl category;
    private Integer cost;
    private Integer id;
    private String name;

    public ItemDto() {}

    public ItemDto(List<NameAndUrl> attributes, NameAndUrl category, Integer cost, Integer id, String name) {
        this.attributes = attributes;
        this.category = category;
        this.cost = cost;
        this.id = id;
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

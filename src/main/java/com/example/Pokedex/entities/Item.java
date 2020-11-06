package com.example.Pokedex.entities;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

public class Item implements Serializable {
    private static final long serialVersionUID = 7270035226459372508L;

    @Id
    @ApiModelProperty( notes = "The MongoDb _id" )
    private String id;
    @ApiModelProperty( notes = "The item's id on PokeApi" )
    private Integer poke_api_id;
    @ApiModelProperty( notes = "Contains a item's attributes" )
    private List<NameAndUrl> attributes;
    @ApiModelProperty( notes = "The item's category" )
    private NameAndUrl category;
    @ApiModelProperty( notes = "The item's cost" )
    private Integer cost;
    @ApiModelProperty( notes = "The item's name" )
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

package com.example.Pokedex.entities;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

public class Pokemon implements Serializable {

    private static final long serialVersionUID = 1465007962309070468L;
    @Id
    @NotNull
    @ApiModelProperty( notes = "The MongoDb _id" )
    private String id;
    @ApiModelProperty( notes = "Contains a pokemon's abilities" )
    private List<PokemonAbility> abilities;
    @ApiModelProperty( notes = "The pokemon's base experience" )
    private Integer base_experience;
    @ApiModelProperty( notes = "Contains a pokemon's forms" )
    private List<NameAndUrl> forms;
    @ApiModelProperty( notes = "Contains a pokemon's game indecencies" )
    private List<GameIndicy> game_indices;
    @ApiModelProperty( notes = "The pokemon's height" )
    private Integer height;
    @ApiModelProperty( notes = "Contains a pokemon's items" )
    private List<PokemonItem> held_items;
    @ApiModelProperty( notes = "The pokemon's id on PokeApi" )
    private String poke_api_id;
    @ApiModelProperty( notes = "If pokemon is default" )
    private Boolean is_default;
    @ApiModelProperty( notes = "Url to PokeApi's location_area_encounters" )
    private String location_area_encounters;
    @ApiModelProperty( notes = "Contains a pokemon's moves" )
    private List<PokemonMove> moves;
    @ApiModelProperty( notes = "The pokemon's name" )
    private String name;
    @ApiModelProperty( notes = "Contains a pokemon's types" )
    private List<PokemonType> types;
    @ApiModelProperty( notes = "The pokemon's order" )
    private Integer order;
    @ApiModelProperty( notes = "The pokemon's weight" )
    private Integer weight;

    public Pokemon() {}

    public Pokemon(

            List<PokemonAbility> abilities,
            Integer base_experience,
            List<NameAndUrl> forms,
            List<GameIndicy> game_indices,
            Integer height,
            List<PokemonItem> held_items,
            String poke_api_id,
            Boolean is_default,
            String location_area_encounters,
            List<PokemonMove> moves,
            String name,
            List<PokemonType> types,
            Integer order,
            Integer weight
    ) {

        this.abilities = abilities;
        this.base_experience = base_experience;
        this.forms = forms;
        this.game_indices = game_indices;
        this.height = height;
        this.held_items = held_items;
        this.poke_api_id = poke_api_id;
        this.is_default = is_default;
        this.location_area_encounters = location_area_encounters;
        this.moves = moves;
        this.name = name;
        this.types = types;
        this.order = order;
        this.weight = weight;
        
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
    public String getPoke_api_id() {
        return poke_api_id;
    }
    public void setPoke_api_id(String poke_api_id) {
        this.poke_api_id = poke_api_id;
    }
    public List<PokemonAbility> getAbilities() {
        return abilities;
    }
    public void setAbilities(List<PokemonAbility> abilities) {
        this.abilities = abilities;
    }
    public Integer getBase_experience() {
        return base_experience;
    }
    public void setBase_experience(Integer base_experience) {
        this.base_experience = base_experience;
    }
    public List<NameAndUrl> getForms() {
        return forms;
    }
    public void setForms(List<NameAndUrl> forms) {
        this.forms = forms;
    }
    public List<GameIndicy> getGame_indices() {
        return game_indices;
    }
    public void setGame_indices(List<GameIndicy> game_indices) {
        this.game_indices = game_indices;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public List<PokemonItem> getHeld_items() {
        return held_items;
    }
    public void setHeld_items(List<PokemonItem> held_items) {
        this.held_items = held_items;
    }
    public Boolean getIs_default() {
        return is_default;
    }
    public void setIs_default(Boolean is_default) {
        this.is_default = is_default;
    }
    public String getLocation_area_encounters() {
        return location_area_encounters;
    }
    public void setLocation_area_encounters(String location_area_encounters) {
        this.location_area_encounters = location_area_encounters;
    }
    public List<PokemonMove> getMoves() {
        return moves;
    }
    public void setMoves(List<PokemonMove> moves) {
        this.moves = moves;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<PokemonType> getTypes() {
        return types;
    }
    public void setTypes(List<PokemonType> types) {
        this.types = types;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}

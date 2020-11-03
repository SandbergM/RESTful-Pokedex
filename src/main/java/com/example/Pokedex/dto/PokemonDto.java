package com.example.Pokedex.dto;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-26
*/

import com.example.Pokedex.entities.*;
import java.util.List;

public class PokemonDto {

    private List<PokemonAbility> abilities;
    private Integer base_experience;
    private List<NameAndUrl> forms;
    private List<GameIndicy> game_indices;
    private Integer height;
    private List<PokemonItem> held_items;
    private Integer id;
    private Boolean is_default;
    private String location_area_encounters;
    private List<PokemonMove> moves;
    private String name;
    private List<PokemonType> types;
    private Integer order;
    private Integer weight;

    public PokemonDto() { }

    public PokemonDto(
            List<PokemonAbility> abilities,
            Integer base_experience,
            List<NameAndUrl> forms,
            List<GameIndicy> game_indices,
            Integer height,
            List<PokemonItem> held_items,
            Integer id,
            Boolean is_default,
            String location_area_encounters,
            List<PokemonMove> moves, String name,
            List<PokemonType> types,
            Integer order,
            Integer weight) {

        this.abilities = abilities;
        this.base_experience = base_experience;
        this.forms = forms;
        this.game_indices = game_indices;
        this.height = height;
        this.held_items = held_items;
        this.id = id;
        this.is_default = is_default;
        this.location_area_encounters = location_area_encounters;
        this.moves = moves;
        this.name = name;
        this.types = types;
        this.order = order;
        this.weight = weight;
    }

    public List<PokemonAbility> getAbilities() {
        return abilities;
    }
    public Integer getBase_experience() {return base_experience;}
    public List<NameAndUrl> getForms() {return forms;}
    public List<GameIndicy> getGame_indices() {return game_indices;}
    public Integer getHeight() {return height;}
    public List<PokemonItem> getHeld_items() {return held_items;}
    public Integer getId() {return id;}
    public Boolean getIs_default() {return is_default;}
    public String getLocation_area_encounters() {return location_area_encounters;}
    public List<PokemonMove> getMoves() {return moves;}
    public String getName() {return name;}
    public List<PokemonType> getTypes() {return types;}
    public Integer getOrder() {return order;}
    public Integer getWeight() {return weight;}

}



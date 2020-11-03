package com.example.Pokedex.entities;

import org.springframework.data.annotation.Id;

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
    private String id;
    private List<PokemonAbility> abilities;
    private Integer base_experience;
    private List<PokemonForm> forms;
    private List<GameIndicy> game_indices;
    private Integer height;
    private List<PokemonItem> held_items;
    private Integer poke_api_id;
    private Boolean is_default;
    private String location_area_encounters;
    private List<PokemonMove> moves;
    private String name;
    private List<PokemonType> types;
    private Integer order;
    private Integer weight;

    public Pokemon(String name, List<PokemonAbility> abilities, Integer base_experience, List<PokemonForm> forms, List<GameIndicy> game_indices, Integer height, List<PokemonItem> held_items, Integer poke_api_id, Boolean is_default, String location_area_encounters, List<PokemonMove> moves, List<PokemonType> types, Integer order, Integer weight) {
        this.name = name;
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
        this.types = types;
        this.order = order;
        this.weight = weight;
    }

    public Pokemon() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<PokemonForm> getForms() {
        return forms;
    }

    public void setForms(List<PokemonForm> forms) {
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

    public Integer getPoke_api_id() {
        return poke_api_id;
    }

    public void setPoke_api_id(Integer poke_api_id) {
        this.poke_api_id = poke_api_id;
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

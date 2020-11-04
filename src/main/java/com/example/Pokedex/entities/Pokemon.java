package com.example.Pokedex.entities;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Pokemon unique identifier id",example = "5fa265933abf5a24d97169d7", required = true)
    @Id
    @NotNull
    private String id;
    @Schema(description = "Array with a pokemon's ability's")
    private List<PokemonAbility> abilities;
    @Schema(description = "A pokemon's base experience",example = "285")
    private Integer base_experience;
    @Schema(description = "Array with a pokemon's forms")
    private List<NameAndUrl> forms;
    private List<GameIndicy> game_indices;
    @Schema(description = "The pokemon's height", example = "17")
    private Integer height;
    @Schema(description = "Array with the items a pokemon possesses")
    private List<PokemonItem> held_items;
    @Schema(description = "Pokemon unique identifier id from PokeApi", example = "10034")
    private String poke_api_id;
    @Schema(description = "Shows if the pokemon is default to the game or not")
    private Boolean is_default;
    @Schema(description = "Url to the location the pokemon might be found in",
            example = "https://pokeapi.co/api/v2/pokemon/10034/encounters")
    private String location_area_encounters;
    @Schema(description = "Array with all of the moves the pokemon has")
    private List<PokemonMove> moves;
    @Schema(description = "The pokemon's name", example = "Charizard")
    private String name;
    @Schema(description = "Array with all of the types the pokemon belongs to")
    private List<PokemonType> types;
    @Schema(description = "The pokemon's order in the in game pokedex", example = "9")
    private Integer order;
    @Schema(description = "The pokemon's weight", example = "905")
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

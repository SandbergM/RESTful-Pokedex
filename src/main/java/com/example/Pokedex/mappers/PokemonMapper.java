package com.example.Pokedex.mappers;

import com.example.Pokedex.dto.PokemonDto;
import com.example.Pokedex.entities.Pokemon;
import org.mapstruct.Mapper;

@Mapper
public interface PokemonMapper {
    default Pokemon map(PokemonDto pokemonDto){
        return new Pokemon(
                pokemonDto.getAbilities(),
                pokemonDto.getBase_experience(),
                pokemonDto.getForms(),
                pokemonDto.getGame_indices(),
                pokemonDto.getHeight(),
                pokemonDto.getHeld_items(),
                pokemonDto.getId(),
                pokemonDto.getIs_default(),
                pokemonDto.getLocation_area_encounters(),
                pokemonDto.getMoves(),
                pokemonDto.getName(),
                pokemonDto.getTypes(),
                pokemonDto.getOrder(),
                pokemonDto.getWeight()
        );
    }
}
package com.example.Pokedex.mappers;

import com.example.Pokedex.dto.PokemonDto;
import com.example.Pokedex.entities.Pokemon;
import org.mapstruct.Mapper;

@Mapper
public interface PokemonMapper { Pokemon pokemonDtoToPokemon(PokemonDto pokemonDto); }
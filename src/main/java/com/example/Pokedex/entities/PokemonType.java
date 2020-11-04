package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-01
*/

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class PokemonType implements Serializable {
    private static final long serialVersionUID = -833422101021159608L;
    @Schema(description = "The slot of the pokemon in the PokemonType array", example = "1")
    private int slot;
    private NameAndUrl type;


    public PokemonType(int slot, NameAndUrl type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    public NameAndUrl getType() { return type; }
    public void setType(NameAndUrl type) { this.type = type; }

}

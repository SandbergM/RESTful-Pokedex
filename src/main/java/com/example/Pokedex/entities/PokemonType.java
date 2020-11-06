package com.example.Pokedex.entities;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-01
*/

public class PokemonType implements Serializable {
    private static final long serialVersionUID = -833422101021159608L;
    @ApiModelProperty( notes = "Shows what slot the type is in" )
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

package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-01
*/

import java.io.Serializable;
import java.util.HashMap;

public class PokemonType implements Serializable {
    private static final long serialVersionUID = -833422101021159608L;
    private int slot;
    private HashMap<String, String> type;


    public PokemonType(int slot, HashMap<String, String> type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    public HashMap<String, String> getType() { return type; }
    public void setType(HashMap<String, String> type) { this.type = type; }

}

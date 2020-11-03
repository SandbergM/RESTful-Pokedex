package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import java.io.Serializable;
import java.util.HashMap;

public class PokemonAbility implements Serializable {

    private static final long serialVersionUID = -7904366260911860606L;
    private HashMap<String, String> ability;
    private boolean is_hidden;
    private int slot;

    public PokemonAbility(HashMap<String, String> ability, boolean is_hidden, int slot) {
        this.ability = ability;
        this.is_hidden = is_hidden;
        this.slot = slot;
    }

    public HashMap<String, String> getAbility() {
        return ability;
    }
    public void setAbility(HashMap<String, String> ability) {
        this.ability = ability;
    }
    public boolean isIs_hidden() {
        return is_hidden;
    }
    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }
    public int getSlot() {
        return slot;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
}

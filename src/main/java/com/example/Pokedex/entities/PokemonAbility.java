package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import java.io.Serializable;

public class PokemonAbility implements Serializable {

    private static final long serialVersionUID = -7904366260911860606L;
    private NameAndUrl ability;
    private boolean is_hidden;
    private int slot;

    public PokemonAbility(NameAndUrl ability, boolean is_hidden, int slot) {
        this.ability = ability;
        this.is_hidden = is_hidden;
        this.slot = slot;
    }

    public NameAndUrl getAbility() {
        return ability;
    }
    public void setAbility(NameAndUrl ability) {
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

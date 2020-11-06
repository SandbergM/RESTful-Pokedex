package com.example.Pokedex.entities;

import java.io.Serializable;
import java.util.List;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

public class PokemonMove implements Serializable {

    private static final long serialVersionUID = -8897667837980649286L;
    private NameAndUrl move;
    private List<?> version_group_details;

    public PokemonMove(NameAndUrl move, List<?> version_group_details) {
        this.move = move;
        this.version_group_details = version_group_details;
    }

    public NameAndUrl getMove() {
        return move;
    }
    public void setMove(NameAndUrl move) {
        this.move = move;
    }
    public List<?> getVersion_group_details() {
        return version_group_details;
    }
    public void setVersion_group_details(List<?> version_group_details) {
        this.version_group_details = version_group_details;
    }
}

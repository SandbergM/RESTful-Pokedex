package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class GameIndicy implements Serializable {

    @Schema(description = "Game index from PokeApi", example = "180")
    private int game_index;
    private NameAndUrl version;

    public GameIndicy(int game_index, NameAndUrl version) {
        this.game_index = game_index;
        this.version = version;
    }

    public int getGame_index() {
        return game_index;
    }
    public void setGame_index(int game_index) {
        this.game_index = game_index;
    }
    public NameAndUrl getVersion() {
        return version;
    }
    public void setVersion(NameAndUrl version) {
        this.version = version;
    }
}

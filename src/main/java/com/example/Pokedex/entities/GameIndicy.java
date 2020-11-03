package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import java.io.Serializable;
import java.util.HashMap;

public class GameIndicy implements Serializable {

    private int game_index;
    private HashMap<String, String> version;

    public GameIndicy(int game_index, HashMap<String, String> version) {
        this.game_index = game_index;
        this.version = version;
    }

    public int getGame_index() {
        return game_index;
    }

    public void setGame_index(int game_index) {
        this.game_index = game_index;
    }

    public HashMap<String, String> getVersion() {
        return version;
    }

    public void setVersion(HashMap<String, String> version) {
        this.version = version;
    }
}

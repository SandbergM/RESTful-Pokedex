package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

import java.util.HashMap;
import java.util.List;

public class Move {

    private HashMap<String, String> move;
    private List<?> version_group_details;

    public Move(HashMap<String, String> move, List<?> version_group_details) {
        this.move = move;
        this.version_group_details = version_group_details;
    }

    public HashMap<String, String> getMove() {
        return move;
    }

    public void setMove(HashMap<String, String> move) {
        this.move = move;
    }

    public List<?> getVersion_group_details() {
        return version_group_details;
    }

    public void setVersion_group_details(List<?> version_group_details) {
        this.version_group_details = version_group_details;
    }
}

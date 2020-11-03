package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class PokemonItem implements Serializable {

    private static final long serialVersionUID = -8081272136726440200L;
    private NameAndUrl item;
    private List<?> version_details;

    public PokemonItem(NameAndUrl item, List<?> version_details) {
        this.item = item;
        this.version_details = version_details;
    }

    public NameAndUrl getItem() {
        return item;
    }
    public void setItem(NameAndUrl item) {
        this.item = item;
    }
    public List<?> getVersion_details() {
        return version_details;
    }
    public void setVersion_details(List<?> version_details) {
        this.version_details = version_details;
    }
}

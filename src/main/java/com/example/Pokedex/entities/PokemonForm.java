package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-02
*/

import java.io.Serializable;

public class PokemonForm implements Serializable {

    private static final long serialVersionUID = 410066178078995190L;
    private String name;
    private String url;

    public PokemonForm() {}

    public PokemonForm(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

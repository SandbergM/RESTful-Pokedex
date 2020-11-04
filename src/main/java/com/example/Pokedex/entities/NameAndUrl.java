package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class NameAndUrl implements Serializable {
    private static final long serialVersionUID = -1527438535573075202L;

    @Schema(description = "Displays basic data, name", example = "charizard")
    private String name;
    @Schema(description = "Displays basic data, url", example = "https://pokeapi.co/api/v2/pokemon-form/6/")
    private String url;

    public NameAndUrl() {}

    public NameAndUrl(String name, String url) {
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

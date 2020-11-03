package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

import java.io.Serializable;

public class NameAndUrl implements Serializable {
    private static final long serialVersionUID = -1527438535573075202L;
    private String name;
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

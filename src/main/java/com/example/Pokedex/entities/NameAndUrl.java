package com.example.Pokedex.entities;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

public class NameAndUrl implements Serializable {
    private static final long serialVersionUID = -1527438535573075202L;

    @ApiModelProperty( notes = "Basic info, name" )
    private String name;
    @ApiModelProperty( notes = "Basic info, url" )
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

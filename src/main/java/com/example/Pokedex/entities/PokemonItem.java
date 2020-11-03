package com.example.Pokedex.entities;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

import java.util.HashMap;
import java.util.List;

public class Item {

    private HashMap<String, String> item;
    private List<?> version_details;

    public Item(HashMap<String, String> item, List<?> version_details) {
        this.item = item;
        this.version_details = version_details;
    }

    public HashMap<String, String> getItem() {
        return item;
    }

    public void setItem(HashMap<String, String> item) {
        this.item = item;
    }

    public List<?> getVersion_details() {
        return version_details;
    }

    public void setVersion_details(List<?> version_details) {
        this.version_details = version_details;
    }
}

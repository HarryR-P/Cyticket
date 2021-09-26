package com.kk08.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Animal {

    private final UUID uuid;
    private final String name;
    private final String species;

    public Animal(@JsonProperty("id")UUID id, @JsonProperty("name") String name, @JsonProperty("species") String species) {
        this.uuid = id;
        this.name = name;
        this.species = species;
    }

    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

}

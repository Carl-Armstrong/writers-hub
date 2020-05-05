package com.scififics.writershub.models;

import javax.persistence.Entity;

@Entity
public class World extends AbstractEntity{

    public World(String title, String description) {
        super();
    }

    public World() {}
}

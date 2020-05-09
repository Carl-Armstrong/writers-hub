package com.scififics.writershub.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class World extends AbstractEntity{

    @OneToMany
    @JoinColumn
    private List<Story> storyList = new ArrayList<>();

    public World(String title, String description) {
        super();
    }

    public World() {}
}

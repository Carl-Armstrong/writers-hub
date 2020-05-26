package com.scififics.writershub.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story extends AbstractContentEntity{

    @OneToMany
    @JoinColumn
    private List<Chapter> chapterList = new ArrayList<>();

    @ManyToOne
    private World world;

    public Story(String title, String description) {
        super();
    }

    public Story() {}

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}

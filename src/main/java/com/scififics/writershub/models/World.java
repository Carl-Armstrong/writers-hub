package com.scififics.writershub.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class World extends AbstractContentEntity{

    @OneToMany
    @JoinColumn
    private List<Story> storyList = new ArrayList<>();

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    public World(String title, String description) {
        super();
    }

    public World() {}

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {this.tags.add(tag);}
}

package com.scififics.writershub.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story extends AbstractContentEntity{

    @OneToMany(mappedBy = "story")
    private final List<Chapter> chapterList = new ArrayList<>();

    @ManyToOne
    private World world;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

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

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {this.tags.add(tag);}

    public List<Chapter> getChapterList() {
        return chapterList;
    }
}

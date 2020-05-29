package com.scififics.writershub.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity {

    @Size (min = 1, max = 25, message = " - Must be between 1 and 25 characters")
    @NotBlank (message = " - Name cannot be blank")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private final List<Chapter> chapters = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    private final List<Story> stories = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    private final List<World> worlds = new ArrayList<>();

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<Story> getStories() { return stories; }

    public List<World> getWorlds() { return worlds; }

    @Override
    public String toString() {
        return name;
    }
}

package com.scififics.writershub.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn
    private List<Chapter> chapterList = new ArrayList<>();

    private String title;

    private String description;

    public Story(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Story() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
}

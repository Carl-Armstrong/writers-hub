package com.scififics.writershub.models;

import javax.persistence.*;

@Entity
public class Chapter {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Story story;

    private String title;

    @Lob
    private String content;

    public Chapter(String title, String content, Story aStory) {
        this.title = title;
        this.content = content;
        this.story = aStory;
    }

    public Chapter() {}

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }
}

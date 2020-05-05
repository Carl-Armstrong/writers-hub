package com.scififics.writershub.models;

import javax.persistence.*;

@Entity
public class Chapter extends AbstractEntity{

    @ManyToOne
    private Story story;

    @Lob
    private String content;

    public Chapter(String title, String content, Story aStory) {
        super();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

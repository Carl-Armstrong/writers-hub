package com.scififics.writershub.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Chapter extends AbstractEntity{

    @ManyToOne
    private Story story;

    @Lob
    @NotBlank (message = " - Post cannot be left blank")
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

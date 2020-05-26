package com.scififics.writershub.models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chapter extends AbstractContentEntity{

    @ManyToOne
    private Story story;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

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

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
}

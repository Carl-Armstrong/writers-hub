package com.scififics.writershub.models.dto;

import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.Tag;

import javax.validation.constraints.NotNull;

public class StoryTagDTO {

    @NotNull
    private Story story;

    @NotNull
    private Tag tag;

    public StoryTagDTO() {}

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}

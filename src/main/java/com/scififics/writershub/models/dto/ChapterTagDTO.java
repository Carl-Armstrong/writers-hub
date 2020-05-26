package com.scififics.writershub.models.dto;

import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Tag;

import javax.validation.constraints.NotNull;

public class ChapterTagDTO {

    @NotNull
    private Chapter chapter;

    @NotNull
    private Tag tag;

    public ChapterTagDTO() {}

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}

package com.scififics.writershub.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chapter {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String content;

    public Chapter(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Chapter() {}

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

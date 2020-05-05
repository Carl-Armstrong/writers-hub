package com.scififics.writershub.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story extends AbstractEntity{

    @OneToMany
    @JoinColumn
    private List<Chapter> chapterList = new ArrayList<>();

    public Story(String title, String description) {
        super();
    }

    public Story() {}

}

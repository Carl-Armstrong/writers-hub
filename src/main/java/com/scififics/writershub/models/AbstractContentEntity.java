package com.scififics.writershub.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractContentEntity extends AbstractEntity{

    @NotBlank(message = " - Title cannot be blank")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Size(max = 500, message = " - Description too long")
    private String description;

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


    @Override
    public String toString() {
        return title;
    }
}

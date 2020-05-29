package com.scififics.writershub.models.dto;

import com.scififics.writershub.models.Tag;
import com.scififics.writershub.models.World;

import javax.validation.constraints.NotNull;

public class WorldTagDTO {

    @NotNull
    private World world;

    @NotNull
    private Tag tag;

    public WorldTagDTO() {}

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}

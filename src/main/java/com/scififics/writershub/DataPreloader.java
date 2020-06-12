package com.scififics.writershub;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/*
    Upon startup, this class checks the database for data. If none is present, it creates
    a default world object and a default story object
 */
@Component
public class DataPreloader implements CommandLineRunner {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public void run(String...args) throws Exception {

        // Get list of all World objects in the database
        List<World> worldObjs = (List<World>) worldRepository.findAll();

        // Check if list of world objects is empty
        if (worldObjs.isEmpty()) {
            // create default world
            World world = new World();
            world.setTitle("Default");
            world.setDescription("Default");
            worldRepository.save(world);

            // create default story set in default world
            Story story = new Story();
            story.setTitle("Default");
            story.setDescription("Default");
            story.setWorld(world);
            storyRepository.save(story);
        }
    }
}

package com.scififics.writershub;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataPreloader implements CommandLineRunner {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public void run(String...args) throws Exception {

        List<World> worldObjs = (List<World>) worldRepository.findAll();

        if (worldObjs.isEmpty()) {
            World world = new World();
            world.setTitle("Default");
            world.setDescription("Default");
            worldRepository.save(world);

            Story story = new Story();
            story.setTitle("Default");
            story.setDescription("Default");
            story.setWorld(world);
            storyRepository.save(story);
        }
    }
}

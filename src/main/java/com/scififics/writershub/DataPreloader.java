package com.scififics.writershub;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Chapter;
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

    @Autowired
    private ChapterRepository chapterRepository;

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

            // create a post which explains use of this app to user
            Chapter chapter = new Chapter();
            chapter.setTitle("Welcome to Writer's Hub");
            chapter.setDescription("Click here for tips on how to use this app");
            chapter.setStory(story);
            chapter.setContent("Welcome to Writer's Hub!\r\n" +
                    "\r\n" +
                    "Writer's Hub features a blog-like structure that organizes your writing into Worlds, " +
                    "Stories, and Chapters.\r\n" +
                    "\r\n" +
                    "Worlds\r\n" +
                    "A World holds all Stories that share a continuity. For instance, if you were Disney, you might " +
                    "have a World named ‘Marvel’ and another named ‘Star Wars’.\r\n" +
                    "Click on the ‘Worlds’ tab. Enter a title and description. Now click ‘Create New World’. It’s that " +
                    "easy. All Worlds will be listed below. If you click on a World, you will be directed to that " +
                    "World’s page, which lists all Stories within it.\r\n" +
                    "\r\n" +
                    "Stories\r\n" +
                    "The next level down is Story. All Stories fit into a World and are filled with Chapters. To " +
                    "continue our Disney example, ‘Iron Man’ and ‘Guardians of the Galaxy’ would be Stories in the " +
                    "‘Marvel’ World while ‘Episode IV’ and ‘Episode V’ would be Stories in the ‘Star Wars’ World.\r\n" +
                    "Click on the ‘Stories’ tab. Enter a title and description and select the World you want your Story " +
                    "to belong to. Now click ‘Create New Story’. All Stories will be listed below. If you click on a " +
                    "Story, you will be directed to that Story’s page, which lists all Chapters within it.\r\n" +
                    "\r\n" +
                    "Chapters\r\n" +
                    "Chapters are the lowest level of the Writer’s Hub structure. This is where you will create the " +
                    "actual content to be read.\r\n" +
                    "Click on the ‘Chapters’ tab. You will see a list of all Chapters. You may view, edit, or delete any " +
                    "of the Chapters by pressing the appropriate button. To create a Chapter, click the ‘Create New Post’ " +
                    "link at the top. Fill in the title and description and select the Story your chapter belongs to. " +
                    "And of course, paste or type your chapter in the large box. When you are done, click ‘Post’ at the " +
                    "bottom of the page. You will be redirected to the home page where you will see a list of all " +
                    "Chapters. Click on any of them to read them.\r\n" +
                    "\r\n" +
                    "Tags\r\n" +
                    "As you were adding Worlds, Stories, and Chapters, you probably noticed the Tags. If there are " +
                    "already tags listed, you can check any of their boxes to include them. Or you can click ‘Add Tags’ " +
                    "and create new tags.\r\n"
            );
            chapterRepository.save(chapter);
        }
    }
}

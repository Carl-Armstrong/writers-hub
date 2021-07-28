package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.Tag;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("createspace/storyscape")
public class StoryscapeController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("")
    public String viewIndex(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("pageTitle", "All Stories");
        return "createspace/storyscape/index";
    }

    @PostMapping("")
    public String processIndexForm(Model model, @RequestParam String buttonFunction,
                                   @RequestParam int storyId) {

        if (buttonFunction.equals("delete")) {
            // Get story that will be deleted and list of all chapters within that story
            Optional<Story> oldStory = storyRepository.findById(storyId);
            List<Chapter> displacedChapters = oldStory.get().getChapterList();

            // Get the first story in the list of all stories aka the default story
            List<Story> allStories = (List<Story>) storyRepository.findAll();
            Story newStory = allStories.get(0);

            // if the story to be deleted is the default story, nothing will happen
            // else all chapters are assigned to the default story
            if (oldStory.get().equals(newStory)) {
                // nothing happens - cannot delete default story
                // will add custom error message later
            } else {
                for (Chapter chapter : displacedChapters) {
                    chapter.setStory(newStory);
                }
                storyRepository.deleteById(storyId);
            }


        }

        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("pageTitle", "All Stories");
        return "createspace/storyscape/index";
    }

    @GetMapping("storybuilder")
    public String renderCreateStoryForm(Model model) {
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("pageTitle", "Create New Story");
        model.addAttribute(new Story());
        return "createspace/storyscape/storybuilder";
    }

    @PostMapping("storybuilder")
    public String processCreateStoryForm(@ModelAttribute @Valid Story newStory, Errors errors,
                                         Model model, @RequestParam int worldId,
                                         @RequestParam(required = false) List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("worlds", worldRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            model.addAttribute("pageTitle", "Create New Story");
            return "createspace/storyscape/storybuilder";
        }

        if (tags != null) {
            List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

            for (Tag tag : tagObjs) {
                newStory.addTag(tag);
            }
        }

        Optional<World> worldResult = worldRepository.findById(worldId);
        if (worldResult.isEmpty()) {
            // set world to the first world in the repository aka the default world
            List<World> allWorlds = (List<World>) worldRepository.findAll();
            World newWorld = allWorlds.get(0);
            newStory.setWorld(newWorld);
        } else {
            World world = worldResult.get();
            newStory.setWorld(world);
        }

        storyRepository.save(newStory);
        return "redirect:../../storyview/storyviewer/" + newStory.getId();
    }

    @GetMapping("storybreaker/{storyId}")
    public String renderEditStoryForm(Model model, @PathVariable int storyId) {

        Optional optStory = storyRepository.findById(storyId);

        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        if (optStory.isPresent()) {
            Story story = (Story) optStory.get();
            model.addAttribute("pageTitle", "Editing " + story.getTitle());
            model.addAttribute("story", story);
            return "createspace/storyscape/storybreaker";
        } else {
            return "redirect:../createspace/storyscape/index/";
        }
    }

    @PostMapping("storybreaker")
    public String processEditStoryForm(@ModelAttribute @Valid Story editedStory, Errors errors, Model model,
                                       @RequestParam int worldId, @RequestParam(required = false) List<Integer> tags,
                                       @RequestParam int storyId) {
        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            return "redirect:../createspace/storyscape/storybreaker/" + storyId;
        }

        Optional optStory = storyRepository.findById(storyId);

        if (optStory.isPresent()) {
            Story story = (Story) optStory.get();

            story.setTitle(editedStory.getTitle());
            story.setDescription(editedStory.getDescription());

            if (tags != null) {
                List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

                for (Tag tag : tagObjs) {
                    story.addTag(tag);
                }
            }

            Optional<World> worldResult = worldRepository.findById(worldId);
            if (worldResult.isEmpty()) {
                // set world to the first world in the repository aka the default world
                List<World> allWorlds = (List<World>) worldRepository.findAll();
                World newWorld = allWorlds.get(0);
                story.setWorld(newWorld);
            } else {
                World world = worldResult.get();
                story.setWorld(world);
            }

            storyRepository.save(story);
            return "redirect:../storybreaker/" + storyId;
        } else {
            return "redirect:../createspace/storyscape/index/";
        }
    }
}

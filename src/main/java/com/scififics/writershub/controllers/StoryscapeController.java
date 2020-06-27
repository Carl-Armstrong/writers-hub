package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}

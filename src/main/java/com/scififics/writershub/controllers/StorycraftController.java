package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("storycraft")
public class StorycraftController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("storyhub")
    public String renderStoryhubForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute(new Story());
        model.addAttribute("tags", tagRepository.findAll());
        return "storycraft/storyhub";
    }

    @PostMapping("storyhub")
    public String processStoryhubForm(@ModelAttribute @Valid Story newStory, Errors errors, Model model,
                                      @RequestParam int worldId, @RequestParam(required = false) List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            model.addAttribute("worlds", worldRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            return "storycraft/storyhub";
        }

        if (tags != null) {
            List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

            for (Tag tag : tagObjs) {
                newStory.addTag(tag);
            }
        }

        Optional<World> worldResult = worldRepository.findById(worldId);
        if (worldResult.isEmpty()) {
            // Do nothing for now. I'll fix this later
        } else {
            World world = worldResult.get();
            newStory.setWorld(world);
        }

        storyRepository.save(newStory);
        return "redirect:../storycraft/storyhub";
    }

    @RequestMapping(value= "storyview/{storyId}")
    public String viewStoryview(Model model, @PathVariable int storyId) {

        Optional optStory = storyRepository.findById(storyId);

        if (optStory.isPresent()) {
            Story story = (Story) optStory.get();
            model.addAttribute("story", story);
            return "storycraft/storyview";
        } else {
            return "redirect:../";
        }
    }

}

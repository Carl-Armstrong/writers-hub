package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.ChapterData;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("storyhub")
    public String renderStoryhubForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute(new Story());
        return "storycraft/storyhub";
    }

    @PostMapping("storyhub")
    public String processStoryhubForm(@ModelAttribute @Valid Story newStory, Errors errors, Model model,
                                      @RequestParam int worldId) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            model.addAttribute("worlds", worldRepository.findAll());
            return "storycraft/storyhub";
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

    @RequestMapping(value= "storyview")
    public String viewStoryview(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Chapter> chapters;
        if (column.toLowerCase().equals("all")){
            chapters = chapterRepository.findAll();
        } else {
            chapters = ChapterData.findByColumnAndValue(column, value, chapterRepository.findAll());
        }
        model.addAttribute("chapters", chapters);

        return "storycraft/storyview";
    }

}

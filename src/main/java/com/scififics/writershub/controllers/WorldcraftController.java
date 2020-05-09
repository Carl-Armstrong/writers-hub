package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("worldcraft")
public class WorldcraftController {
    @Autowired
    private WorldRepository worldRepository;

    private StoryRepository storyRepository;

    @GetMapping("worldhub")
    public String renderWorldhubForm(Model model) {
        model.addAttribute("worlds", worldRepository.findAll());
        return "worldcraft/worldhub";
    }

    @PostMapping("worldhub")
    public String processWorldhubForm(@ModelAttribute World newWorld, Model model) {
        worldRepository.save(newWorld);
        return "redirect:../worldcraft/worldhub";
    }

    @RequestMapping(value = "worldview")
    public String viewWorldView(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Story> stories;
        if (column.toLowerCase().equals("all")){
            stories = storyRepository.findAll();
        } else {
            stories = StoryData.findByColumnAndValue(column, value, storyRepository.findAll());
        }
        model.addAttribute("stories", stories);

        return "worldcraft/worldview";
    }

}

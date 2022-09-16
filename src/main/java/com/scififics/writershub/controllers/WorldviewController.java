package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("worldview")
public class WorldviewController {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("worldlist")
    public String viewWorldlist(Model model) {
        model.addAttribute("pageTitle", "World List");
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "worldview/worldlist";
    }

    @RequestMapping(value = "worldviewer/{worldId}")
    public String viewWorldviewer(Model model, @PathVariable int worldId) {

        Optional optWorld = worldRepository.findById(worldId);

        if (optWorld.isPresent()) {
            World world = (World) optWorld.get();
            model.addAttribute("world", world);
            model.addAttribute("pageTitle", world.getTitle());
            return "worldview/worldviewer";
        } else {
            return "redirect../worldview/worldlist";
        }
    }
}

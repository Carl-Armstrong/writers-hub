package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
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
@RequestMapping("worldcraft")
public class WorldcraftController {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("worldhub")
    public String renderWorldhubForm(Model model) {
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute(new World());
        model.addAttribute("tags", tagRepository.findAll());
        return "worldcraft/worldhub";
    }

    @PostMapping("worldhub")
    public String processWorldhubForm(@ModelAttribute @Valid World newWorld, Errors errors, Model model,
                                      @RequestParam(required = false) List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("worlds", worldRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            return "worldcraft/worldhub";
        }

        if (tags != null) {
            List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

            for (Tag tag : tagObjs) {
                newWorld.addTag(tag);
            }
        }

        worldRepository.save(newWorld);
        return "redirect:../worldcraft/worldhub";
    }

    @RequestMapping(value = "worldview/{worldId}")
    public String viewWorldView(Model model, @PathVariable int worldId) {

        Optional optWorld = worldRepository.findById(worldId);

        if (optWorld.isPresent()) {
            World world = (World) optWorld.get();
            model.addAttribute("world", world);
            return "worldcraft/worldview";
        } else {
            return "redirect../";
        }
    }

}

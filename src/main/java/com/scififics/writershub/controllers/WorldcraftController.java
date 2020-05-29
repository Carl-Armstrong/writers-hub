package com.scififics.writershub.controllers;

import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.StoryData;
import com.scififics.writershub.models.Tag;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
                                      @RequestParam List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("worlds", worldRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            return "worldcraft/worldhub";
        }

        List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

        for (Tag tag : tagObjs) {
            newWorld.addTag(tag);
        }

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

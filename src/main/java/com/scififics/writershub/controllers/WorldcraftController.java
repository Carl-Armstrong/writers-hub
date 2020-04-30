package com.scififics.writershub.controllers;

import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("worldcraft")
public class WorldcraftController {
    @Autowired
    private WorldRepository worldRepository;

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
}

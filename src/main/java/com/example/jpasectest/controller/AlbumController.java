package com.example.jpasectest.controller;

import com.example.jpasectest.model.Album;
import com.example.jpasectest.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/album")
public class AlbumController {

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService){
        this.albumService = albumService;
    }

    @GetMapping("/add")
    public String showAddAlbumForm(Model model){
        model.addAttribute("album", new Album());
        return "addAlbum";
    }

    @PostMapping("/add")
    public String addAlbum(@ModelAttribute("album") Album album){
        LocalDate releaseDate = LocalDate.of(2014, 2, 14);
        album.setReleaseDate(releaseDate);
        if(albumService.addAlbum(album)){
            System.out.println("Album added successfully");
            System.out.println(album.getReleaseDate().toString());
            return "redirect:/review/all";
        } else {
            System.out.println("Album not added. Please check if it was added before");
            return "redirect:/add";
        }
    }


}

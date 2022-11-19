package com.example.jpasectest.controller;


import com.example.jpasectest.model.Album;
import com.example.jpasectest.model.Review;
import com.example.jpasectest.model.User;
import com.example.jpasectest.service.AlbumService;
import com.example.jpasectest.service.ReviewService;
import com.example.jpasectest.service.UserService;
import com.example.jpasectest.spotify.SpotifyAPiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final AlbumService albumService;
    private final SpotifyAPiImpl spotifyAPi;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, AlbumService albumService, SpotifyAPiImpl spotifyAPi){
        this.reviewService = reviewService;
        this.userService = userService;
        this.albumService = albumService;
        this.spotifyAPi = spotifyAPi;
    }


    @PostMapping("/post")
    public String showForm(Model model){


        model.addAttribute("review", new Review());
        model.addAttribute("albums", albumService.findAll());

        return "post";
    }

    @PostMapping
    public String postReview(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("review") Review review){

        String authorEmail = userDetails.getUsername();
        User author = userService.findByEmail(authorEmail);
        review.setAuthor(author);

        System.out.println(review.getAlbum());



        if(review.getTitle().isEmpty()){
          return "redirect:/review/all";
        }
        reviewService.saveReview(review);
        return "redirect:/review/all";
    }

    @GetMapping("/mine")
    public String showMyReviews(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String authorEmail = userDetails.getUsername();
        User author = userService.findByEmail(authorEmail);
        //List<Review> myReviews = reviewService.findByAuthor(author);
        List<Review> myReviews = author.getUserReviews();
        model.addAttribute("myReviews", myReviews);
        return "myReviews";

    }
    @GetMapping("/all")
    public String showAllReviews(Model model){
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "allReviews";
    }

    @GetMapping("/{id}")
    public String openReview(@PathVariable(value = "id")Long id, Model model){
        Optional<Review> review = reviewService.findById(id);
        if(review.isEmpty()){
            return "post";
        }
        model.addAttribute("review", review);
        return "reviewRead";
    }

    @GetMapping("/album/{id}")
    public String albumInfo(@PathVariable(value = "id")Long id, Model model) throws IOException {

        Optional<Album> album = albumService.findById(id);
        System.out.println(spotifyAPi.getAlbumUrl(album.get().getArtist(), album.get().getName()));
        if (album.isEmpty()){
            return "allReviews";
        }
        model.addAttribute("album", album);
        return "albumInfo";
    }


}

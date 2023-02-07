package com.example.jpasectest.service;
import com.example.jpasectest.model.Album;
import com.example.jpasectest.model.Review;
import com.example.jpasectest.model.User;
import com.example.jpasectest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();                I moved this to controller
//        //review.setAuthor(auth.getName());

        reviewRepository.save(review);
    }

    public List<Review> findByAuthor(User author){
        return reviewRepository.findByAuthor(author);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id){
        return reviewRepository.findById(id);
    }

    public List<Review> findByAlbum(Album album){return reviewRepository.findByAlbum(album);}


}

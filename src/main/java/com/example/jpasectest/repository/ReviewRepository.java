package com.example.jpasectest.repository;

import com.example.jpasectest.model.Review;
import com.example.jpasectest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findByAuthor(User author);

    List<Review> findAll();

}

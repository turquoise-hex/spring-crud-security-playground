package com.example.jpasectest.repository;

import com.example.jpasectest.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    public List<Album> findAll();


}

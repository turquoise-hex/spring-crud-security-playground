package com.example.jpasectest.service;

import com.example.jpasectest.model.Album;
import com.example.jpasectest.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<String> findAllAlbumNames(){
        List<Album> albums = albumRepository.findAll();
        List<String> albumNames = new ArrayList();

        for (Album album : albums){
            albumNames.add(album.getArtist() + " " + album.getName());
        }
        return albumNames;

    }

    public List<Album> findAll(){
        return albumRepository.findAll();
    }


    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }
}

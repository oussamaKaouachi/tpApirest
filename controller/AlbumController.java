package com.myAudioLibrairie.apirest.controller;

import com.myAudioLibrairie.apirest.model.Album;
import com.myAudioLibrairie.apirest.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json",
            value = ""
    )
    public Album addAlbum(@RequestBody Album album){
        return albumRepository.save(album);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id){
        albumRepository.delete(id);
    }

}

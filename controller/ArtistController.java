package com.myAudioLibrairie.apirest.controller;

import com.myAudioLibrairie.apirest.model.Album;
import com.myAudioLibrairie.apirest.model.Artist;
import com.myAudioLibrairie.apirest.repository.AlbumRepository;
import com.myAudioLibrairie.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping("/{id}")
    public Artist findArtistById(@PathVariable("id") Integer id) throws EntityNotFoundException {
        if (!artistRepository.exists(id)){
            throw new EntityNotFoundException("L'artiste d'identifiant "+id+" n'existe pas !");
        }
        return artistRepository.findArtistById(id);
    }

    @RequestMapping(value = "", params = {"name","page", "size", "sortProperty", "sortDirection"})
    public Page<Artist> findArtistByName(@RequestParam String name,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size,
                                         @RequestParam String sortProperty,
                                         @RequestParam String sortDirection ){
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return artistRepository.findArtistByNameContaining(name, pageRequest);
    }

    @RequestMapping(value="",params = {"page", "size", "sortProperty", "sortDirection"})
    public Page<Artist> findArtistPaging(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection
    ){
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return artistRepository.findAll(pageRequest);

    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json",
            value = ""
    )
    public Artist createArtist(@RequestBody Artist artist) throws EntityExistsException {
        if (artistRepository.findArtistByName(artist.getName()) != null){
            throw new EntityExistsException("L'artiste ayant pour nom "+artist.getName()+" existe déjà !");
        }
        return artistRepository.save(artist);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json",
            value = "/{id}"
    )
    public Artist updateArtist(@RequestBody Artist artist) throws EntityNotFoundException {

        if (!artistRepository.exists(artist.getId())){
            throw new EntityNotFoundException("L'artiste d'identifiant "+artist.getId()+" n'existe pas !");
        }

        return artistRepository.save(artist);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id) throws EntityNotFoundException {

        if (!artistRepository.exists(id)){
            throw new EntityNotFoundException("L'artiste d'identifiant "+id+" n'existe pas !");
        }
        Artist a = artistRepository.findOne(id);
        List<Album> albums = a.getAlbums();
        albumRepository.delete(albums);
        artistRepository.delete(id);
    }

}

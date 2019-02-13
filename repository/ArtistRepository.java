package com.myAudioLibrairie.apirest.repository;


import com.myAudioLibrairie.apirest.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer>, PagingAndSortingRepository<Artist, Integer> {

    @EntityGraph(attributePaths = "albums")
    Artist findArtistById(Integer id);

    @EntityGraph(attributePaths = "albums")
    Page<Artist> findArtistByNameContaining(String name, Pageable pageable);

    Artist findArtistByName(String name);
}

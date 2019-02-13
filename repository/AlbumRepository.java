package com.myAudioLibrairie.apirest.repository;

import com.myAudioLibrairie.apirest.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer>, PagingAndSortingRepository<Album, Integer> {
}

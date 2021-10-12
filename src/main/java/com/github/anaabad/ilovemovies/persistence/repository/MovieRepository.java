package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
        List<MovieEntity> findByGenre(String genre);
        @Query(
                value = "SELECT * FROM movie JOIN actor a on :actorId = a.id",
                nativeQuery = true)
        List<MovieEntity> findByActor(@Param("actorId")Long actorId);
}

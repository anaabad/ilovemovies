package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}

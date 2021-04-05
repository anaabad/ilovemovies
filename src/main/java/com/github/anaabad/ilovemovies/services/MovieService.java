package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieService {
    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieEntity> getAll(){
        return movieRepository.findAll();
    }

    public Optional<MovieEntity> getById(Long id){
        return movieRepository.findById(id);
    }

    public MovieEntity save(MovieEntity movieEntity){
        return movieRepository.save(movieEntity);
    }

    public void delete(MovieEntity movieEntity){
        movieRepository.delete(movieEntity);
    }
}

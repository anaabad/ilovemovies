package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import com.github.anaabad.ilovemovies.services.transformers.MovieTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    MovieRepository movieRepository;
    MovieTrf movieTrf;

    public List<MovieDto> getAll() {

        return movieRepository.findAll()
                .stream()
                .map(movieEntity -> movieTrf.movieEntityToMovieDto(movieEntity))
                .collect(Collectors.toList());
    }

    public Optional<MovieDto> getById(Long id) {
        Optional<MovieEntity> movieOpt = movieRepository.findById(id);
        return Optional.of(movieOpt.map(movie -> movieTrf.movieEntityToMovieDto(movie))).orElse(null);
    }

    public MovieDto save(MovieDto movieDto) {
        MovieEntity movie = movieTrf.movieDtoToMovieEntity(movieDto);
        return movieTrf.movieEntityToMovieDto(movieRepository.save(movie));
    }

    public void delete(MovieDto movieDto) {
        movieRepository.delete(movieTrf.movieDtoToMovieEntity(movieDto));
    }
}

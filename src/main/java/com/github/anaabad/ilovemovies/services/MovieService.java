package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import com.github.anaabad.ilovemovies.services.transformers.MovieTrf;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieTrf movieTrf;

    public List<MovieDto> getAll() {

        return movieRepository.findAll()
                .stream()
                .map(movieEntity -> movieTrf.movieEntityToMovieDto(movieEntity))
                .collect(Collectors.toList());
    }

    public MovieDto getById(Long id) throws NotFoundException {
        Optional<MovieEntity> movie = movieRepository.findById(id);
        return movie.map(movieTrf::movieEntityToMovieDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    public MovieDto save(MovieDto movieDto) {
        MovieEntity movie = movieTrf.movieDtoToMovieEntity(movieDto);
        return movieTrf.movieEntityToMovieDto(movieRepository.save(movie));
    }

    public void delete(MovieDto movieDto) {
        movieRepository.delete(movieTrf.movieDtoToMovieEntity(movieDto));
    }

    public MovieDto update(Long id, MovieDto movie) throws NotFoundException {
        MovieDto movieDto = getById(id);
        movieDto.setName(movie.getName());
        movieDto.setDuration(movie.getDuration());
        movieDto.setGenre(movie.getGenre());
        movieDto.setReleaseDate(movie.getReleaseDate());
        return save(movieDto);
    }
}

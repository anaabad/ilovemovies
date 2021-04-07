package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieTrf {
    public MovieEntity movieDtoToMovieEntity(MovieDto movieDto) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(movieDto.getName());
        movieEntity.setDuration(movieDto.getDuration());
        movieEntity.setGenre(movieDto.getGenre());
        movieEntity.setReleaseDate(movieDto.getReleaseDate());
        return movieEntity;
    }

    public MovieDto movieEntityToMovieDto(MovieEntity movieEntity) {
        MovieDto movieDto = new MovieDto();
        movieDto.setName(movieEntity.getName());
        movieDto.setDuration(movieEntity.getDuration());
        movieDto.setGenre(movieEntity.getGenre());
        movieDto.setReleaseDate(movieEntity.getReleaseDate());
        return movieDto;
    }
}

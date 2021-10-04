package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieTrf {
    public final ActorTrf actorTrf;
    public final DirectorTrf directorTrf;

    public MovieEntity movieDtoToMovieEntity(@NotNull MovieDto movieDto) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(movieDto.getName());
        movieEntity.setDuration(movieDto.getDuration());
        movieEntity.setGenre(movieDto.getGenre());
        movieEntity.setReleaseDate(movieDto.getReleaseDate());
        if (movieDto.getActors() != null) {
            movieEntity.setActors(
                    movieDto.getActors().stream()
                            .map(actorTrf::actorDtoToActorEntity)
                            .collect(Collectors.toList()));
        }
        if (movieDto.getDirectors() != null) {
            movieEntity.setDirectors(
                    movieDto.getDirectors().stream()
                            .map(directorTrf::directorDtoToDirectorEntity)
                            .collect(Collectors.toList())
            );
        }
        return movieEntity;
    }

    public MovieDto movieEntityToMovieDto(@NotNull MovieEntity movieEntity) {
        MovieDto movieDto = new MovieDto();
        movieDto.setName(movieEntity.getName());
        movieDto.setDuration(movieEntity.getDuration());
        movieDto.setGenre(movieEntity.getGenre());
        movieDto.setReleaseDate(movieEntity.getReleaseDate());
        if (movieEntity.getDirectors() != null) {
            movieDto.setDirectors(
                    movieEntity.getDirectors().stream()
                            .map(directorTrf::directorEntityToDirectorDto)
                            .collect(Collectors.toList())
            );
        }
        if (movieEntity.getActors() != null) {
            movieDto.setActors(
                    movieEntity.getActors().stream()
                            .map(actorTrf::actorEntityToActorDto)
                            .collect(Collectors.toList())
            );
        }
        return movieDto;
    }
}

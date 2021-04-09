package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieTrf {
    public final ActorTrf actorTrf;
    public final DirectorTrf directorTrf;

    public MovieEntity movieDtoToMovieEntity(MovieDto movieDto) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(movieDto.getName());
        movieEntity.setDuration(movieDto.getDuration());
        movieEntity.setGenre(movieDto.getGenre());
        movieEntity.setReleaseDate(movieDto.getReleaseDate());
        movieEntity.setActors(
                movieDto.getActors().stream()
                        .map(actorTrf::actorDtoToActorEntity)
                        .collect(Collectors.toList()));
        movieEntity.setDirectors(
                movieDto.getDirectors().stream()
                        .map(directorTrf::directorDtoToDirectorEntity)
                        .collect(Collectors.toList())
        );
        return movieEntity;
    }

    public MovieDto movieEntityToMovieDto(MovieEntity movieEntity) {
        MovieDto movieDto = new MovieDto();
        movieDto.setName(movieEntity.getName());
        movieDto.setDuration(movieEntity.getDuration());
        movieDto.setGenre(movieEntity.getGenre());
        movieDto.setReleaseDate(movieEntity.getReleaseDate());
        movieDto.setDirectors(
                movieEntity.getDirectors().stream()
                        .map(directorTrf::directorEntityToDirectorDto)
                        .collect(Collectors.toList())
        );
        movieDto.setActors(
                movieEntity.getActors().stream()
                .map(actorTrf::actorEntityToActorDto)
                .collect(Collectors.toList())
        );
        return movieDto;
    }
}

package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DirectorTrf {

    private final MovieTrf movieTrf;

    public DirectorEntity directorDtoToDirectorEntity(DirectorDto directorDto){
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName(directorDto.getName());
        directorEntity.setNationality(directorDto.getNationality());
        directorEntity.setBirthDate(directorDto.getBirthDate());
        //directorEntity.setMovies(directorDto.getMovies().stream().map(movieTrf::movieDtoToMovieEntity).collect(List.of()));
        return directorEntity;
    }

    public DirectorDto directorEntityToDirectorDto(DirectorEntity directorEntity){
        DirectorDto directorDto = new DirectorDto();
        directorDto.setName(directorEntity.getName());
        directorDto.setNationality(directorEntity.getNationality());
        directorDto.setBirthDate(directorEntity.getBirthDate());
        //directorDto.setMovies(directorDto.getMovies().stream().map(movieTrf::movieDtoToMovieEntity).collect(List.of()));
        return directorDto;
    }
}

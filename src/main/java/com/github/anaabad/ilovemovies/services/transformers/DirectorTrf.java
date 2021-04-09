package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
@RequiredArgsConstructor
public class DirectorTrf {

    public DirectorEntity directorDtoToDirectorEntity(DirectorDto directorDto) {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName(directorDto.getName());
        directorEntity.setNationality(directorDto.getNationality());
        directorEntity.setBirthDate(directorDto.getBirthDate());

        return directorEntity;
    }

    public DirectorDto directorEntityToDirectorDto(DirectorEntity directorEntity) {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setName(directorEntity.getName());
        directorDto.setNationality(directorEntity.getNationality());
        directorDto.setBirthDate(directorEntity.getBirthDate());
        return directorDto;
    }
}

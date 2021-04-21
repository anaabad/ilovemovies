package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import com.github.anaabad.ilovemovies.services.transformers.DirectorTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorTrf directorTrf;

    public DirectorDto findById(Long id) {

        Optional<DirectorEntity> directorOpt = directorRepository.findById(id);
        return directorOpt.map(directorTrf::directorEntityToDirectorDto).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, format("Director with id %s is not found", id)));

    }

    public List<DirectorDto> getAll() {

        return directorRepository.findAll()
                .stream()
                .map(directorEntity -> directorTrf.directorEntityToDirectorDto(directorEntity))
                .collect(Collectors.toList());
    }

    public DirectorDto save(DirectorDto directorDto) {
        DirectorEntity directorEntity = directorRepository.save(directorTrf.directorDtoToDirectorEntity(directorDto));
        return directorTrf.directorEntityToDirectorDto(directorEntity);
    }

    public void delete(Long id) {
        directorRepository.deleteById(id);
    }

    public DirectorDto update(Long id, DirectorDto directorDto){
        DirectorDto director = findById(id);
        director.setName(directorDto.getName());
        director.setBirthDate(directorDto.getBirthDate());
        director.setNationality(directorDto.getNationality());
        return save(director);
    }
}

package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import com.github.anaabad.ilovemovies.services.transformers.DirectorTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorTrf directorTrf;

    public Optional<DirectorDto> findById(Long id) {

        Optional<DirectorEntity> directorOpt = directorRepository.findById(id);
        return directorOpt.map(entity -> Optional.of(directorTrf.directorEntityToDirectorDto(entity))).orElse(null);
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
}

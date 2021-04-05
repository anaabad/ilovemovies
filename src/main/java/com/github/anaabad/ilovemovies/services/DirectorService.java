package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DirectorService {
    DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Optional<DirectorEntity> findById(Long id) {
        return directorRepository.findById(id);
    }

    public List<DirectorEntity> getAll() {
        return directorRepository.findAll();
    }

    public DirectorEntity save(DirectorEntity directorEntity) {
        return directorRepository.save(directorEntity);
    }

    public void delete(Long id) {
        directorRepository.deleteById(id);
    }
}

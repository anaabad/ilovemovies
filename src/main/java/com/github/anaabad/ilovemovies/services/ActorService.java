package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ActorService {

    ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Optional<ActorEntity> findById(Long id) {
        return actorRepository.findById(id);
    }

    public List<ActorEntity> getAll() {
        return actorRepository.findAll();
    }

    public ActorEntity save(ActorEntity actorEntity) {
        return actorRepository.save(actorEntity);
    }

    public void delete(ActorEntity actorEntity) {
        actorRepository.delete(actorEntity);
    }
}

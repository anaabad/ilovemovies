package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.services.transformers.ActorTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    private final ActorTrf actorTrf;

    public Optional<ActorDto> findById(Long id) {
        Optional<ActorEntity> actorEntity = actorRepository.findById(id);
        return actorEntity.map(entity -> Optional.of(actorTrf.actorEntityToActorDto(entity))).orElse(null);
    }

    public List<ActorDto> getAll() {
        return actorRepository.findAll()
                .stream()
                .map(actorTrf::actorEntityToActorDto)
                .collect(Collectors.toList());
    }

    public ActorDto save(ActorDto actorDto) {
        ActorEntity actorEntity = actorTrf.actorDtoToActorEntity(actorDto);
        return actorTrf.actorEntityToActorDto(actorRepository.save(actorEntity));
    }

    public void delete(ActorDto actorDto) {
        actorRepository.delete(actorTrf.actorDtoToActorEntity(actorDto));
    }
}

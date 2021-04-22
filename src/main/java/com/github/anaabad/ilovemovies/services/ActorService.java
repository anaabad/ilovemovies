package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.services.transformers.ActorTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    private final ActorTrf actorTrf;

    public ActorDto findById(Long id) {
        Optional<ActorEntity> actorEntity = actorRepository.findById(id);
        return actorEntity
                .map(actorTrf::actorEntityToActorDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("Actor with ID %s was not found", id)));
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

    public ActorDto update(Long id, ActorDto actorDto){

        ActorDto actor = findById(id);
        actor.setName(actorDto.getName());
        actor.setNationality(actorDto.getNationality());
        actor.setBirthDate(actorDto.getBirthDate());
        actor.setMovies(actorDto.getMovies());

        return save(actor);
    }

    @RolesAllowed("ROLE_ADMIN")
    public void delete(ActorDto actorDto) {
        actorRepository.delete(actorTrf.actorDtoToActorEntity(actorDto));
    }
}

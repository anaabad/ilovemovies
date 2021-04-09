package com.github.anaabad.ilovemovies.services.transformers;


import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActorTrf {

    public ActorEntity actorDtoToActorEntity(ActorDto actorDto) {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName(actorDto.getName());
        actorEntity.setNationality(actorDto.getNationality());
        actorEntity.setBirthDate(actorDto.getBirthDate());

        return actorEntity;
    }

    public ActorDto actorEntityToActorDto(ActorEntity actorEntity) {
        ActorDto actorDto = new ActorDto();
        actorDto.setName(actorEntity.getName());
        actorDto.setBirthDate(actorEntity.getBirthDate());
        actorDto.setNationality(actorEntity.getNationality());
        return actorDto;
    }
}

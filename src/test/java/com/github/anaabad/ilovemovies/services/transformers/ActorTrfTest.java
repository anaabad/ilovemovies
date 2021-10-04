package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ActorTrfTest {

    @InjectMocks
    ActorTrf cut;

    @Test
    void actorDtoToActorEntity() {

        ActorDto actorDto = new ActorDto("Brad Pitt", LocalDate.parse("1963-12-18"), "American", Arrays.asList());
        ActorEntity actorEntity = new ActorEntity("Brad Pitt", LocalDate.parse("1963-12-18"), "American", null);

        assertThat(cut.actorDtoToActorEntity(actorDto)).isEqualToComparingFieldByField(actorEntity);
    }

    @Test
    void actorEntityToActorDto(){

        ActorEntity actorEntity = new ActorEntity("Brad Pitt", LocalDate.parse("1963-12-18"), "American", Arrays.asList());
        ActorDto actorDto = new ActorDto("Brad Pitt", LocalDate.parse("1963-12-18"), "American", null);

        assertThat(cut.actorEntityToActorDto(actorEntity)).isEqualToComparingFieldByField(actorDto);
    }
}

package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DirectorTrfTest {

    @InjectMocks
    private DirectorTrf cut;

    @Test
    void directorDtoToEntity() {
        DirectorDto directorDto = new DirectorDto("James Cameron", "American", LocalDate.parse("1954-08-16"), Arrays.asList());
        DirectorEntity directorEntity = new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), null);

        assertThat(cut.directorDtoToDirectorEntity(directorDto)).isEqualToComparingFieldByField(directorEntity);
    }

    @Test
    void directorEntityToDto() {
        DirectorDto directorDto = new DirectorDto("James Cameron", "American", LocalDate.parse("1954-08-16"), null);
        DirectorEntity directorEntity = new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), Arrays.asList());

        assertThat(cut.directorEntityToDirectorDto(directorEntity)).isEqualToComparingFieldByField(directorDto);
    }
}

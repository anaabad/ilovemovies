package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MovieTrfTest {

    @InjectMocks
    private MovieTrf cut;

    @Test
    void dtoToEntity() {
        MovieDto movieDto = new MovieDto("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        MovieEntity movieEntity= new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        assertThat(cut.movieDtoToMovieEntity(movieDto)).isEqualTo(movieEntity);
    }

    @Test
    void entityToDto(){
        MovieDto movieDto = new MovieDto("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        MovieEntity movieEntity= new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        assertThat(cut.movieEntityToMovieDto(movieEntity)).isEqualTo(movieDto);
    }
}

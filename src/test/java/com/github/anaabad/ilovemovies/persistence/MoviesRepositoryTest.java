package com.github.anaabad.ilovemovies.persistence;

import com.github.anaabad.ilovemovies.common.DbTest;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:/movies.sql")
@DbTest
@ActiveProfiles("test")
public class MoviesRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void findByGenre() {
        List<MovieEntity> movies = movieRepository.findByGenre("Adventure");
        assertThat(movies.size()).isEqualTo(1);
        assertThat(movies.get(0).getName()).isEqualTo("Indiana Jones");
    }
}

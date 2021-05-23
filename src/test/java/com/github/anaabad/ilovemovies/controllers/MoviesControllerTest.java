package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import com.github.anaabad.ilovemovies.services.MovieService;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {MovieService.class})
public class MoviesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @Test
    public void getMovie() throws Exception {

        MovieEntity movieEntity = new MovieEntity("Jurassic Park", LocalDate.parse("1993-10-30"), "Sci-Fi", 128, emptyList(), emptyList(), emptyList());
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("name").value("Jurassic Park")))
                .andExpect(jsonPath("releaseDate").value("1993-10-30"))
                .andExpect((jsonPath("genre").value("Sci-Fi")))
                .andExpect(jsonPath("duration").value(128));

    }

    @Test
    public void getNonExistingMovie() throws Exception {
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMovies() throws Exception {
        List<MovieEntity> movies = Arrays.asList(new MovieEntity("Jurassic Park", LocalDate.parse("1993-10-30"), "Sci-Fi", 128, emptyList(), emptyList(), emptyList()),
                new MovieEntity("Harry Potter and the prisoner of Azkaban", LocalDate.parse("2004-05-18"), "Adventure", 142, emptyList(), emptyList(), emptyList()),
                new MovieEntity("Crossroads", LocalDate.parse("1986-03-14"), "Musical", 94, emptyList(), emptyList(), emptyList()),
                new MovieEntity("Ready Player One", LocalDate.parse("2018-03-29"), "Sci-Fi", 139, emptyList(), emptyList(), emptyList()));

        when(movieRepository.findAll()).thenReturn(movies);
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Jurassic Park"))
                .andExpect(jsonPath("$[1].releaseDate").value("2004-05-18"))
                .andExpect(jsonPath("$[2].genre").value("Musical"))
                .andExpect(jsonPath("$[3].duration").value(139));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void createMovie() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "Enredados")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("directors", new JSONArray())
                .put("actors", new JSONArray());

        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList(), emptyList());
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Enredados"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void updateMovie() throws Exception {

        JSONObject movie = new JSONObject()
                .put("name", "Tangled")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("actors", new JSONArray())
                .put("directors", new JSONArray());

        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList(), emptyList());
        MovieEntity updatedMovieEntity = new MovieEntity("Tangled", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList(), emptyList());
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));
        when(movieRepository.save(updatedMovieEntity)).thenReturn(updatedMovieEntity);
        mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movie.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("name").value("Tangled"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void deleteMovie() throws Exception {
        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList(), emptyList());
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenDeletion() throws Exception {
        mockMvc.perform(delete("/movies/1"))
        .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenCreation() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Enredados")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("directors", new JSONArray())
                .put("actors", new JSONArray());

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isForbidden());
    }
    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenUpdate() throws Exception {
        JSONObject movie = new JSONObject()
                .put("name", "Tangled")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("actors", new JSONArray())
                .put("directors", new JSONArray());

        mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movie.toString()))
                .andExpect(status().isForbidden());
    }
}

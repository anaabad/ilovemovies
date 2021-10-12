package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.*;
import com.github.anaabad.ilovemovies.security.JwtAuthenticationEntryPoint;
import com.github.anaabad.ilovemovies.services.AuthenticationService;
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
import org.springframework.test.context.ActiveProfiles;
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
@ComponentScan(basePackageClasses = {MovieService.class, AuthenticationService.class, JwtAuthenticationEntryPoint.class})
@ActiveProfiles("test")
public class MoviesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmEuYWJhZGFycmFuekBnbWFpbC5jb20iLCJleHAiOjE5MTcwMzMzNDYsImlhdCI6MTUxNjIzOTAyMn0.2d-wBGhNaKWjIWIm9JYi0MTLlsrDzBmHK5BganFD-yE";

    @Test
    public void getMovie() throws Exception {

        MovieEntity movieEntity = new MovieEntity("Jurassic Park", LocalDate.parse("1993-10-30"), "Sci-Fi", 128, emptyList(), emptyList());
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/movies/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect((jsonPath("name").value("Jurassic Park")))
                .andExpect(jsonPath("releaseDate").value("1993-10-30"))
                .andExpect((jsonPath("genre").value("Sci-Fi")))
                .andExpect(jsonPath("duration").value(128));

    }

    @Test
    public void getNonExistingMovie() throws Exception {
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/movies/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMovies() throws Exception {
        List<MovieEntity> movies = Arrays.asList(new MovieEntity("Jurassic Park", LocalDate.parse("1993-10-30"), "Sci-Fi", 128, emptyList(), emptyList()),
                new MovieEntity("Harry Potter and the prisoner of Azkaban", LocalDate.parse("2004-05-18"), "Adventure", 142, emptyList(), emptyList()),
                new MovieEntity("Crossroads", LocalDate.parse("1986-03-14"), "Musical", 94, emptyList(), emptyList()),
                new MovieEntity("Ready Player One", LocalDate.parse("2018-03-29"), "Sci-Fi", 139, emptyList(), emptyList()));

        when(movieRepository.findAll()).thenReturn(movies);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/movies")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Jurassic Park"))
                .andExpect(jsonPath("$[1].releaseDate").value("2004-05-18"))
                .andExpect(jsonPath("$[2].genre").value("Musical"))
                .andExpect(jsonPath("$[3].duration").value(139));
    }

    @Test
    public void createMovie() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "Enredados")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("directors", new JSONArray())
                .put("actors", new JSONArray());

        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Enredados"));
    }

    @Test
    public void updateMovie() throws Exception {

        JSONObject movie = new JSONObject()
                .put("name", "Tangled")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("actors", new JSONArray())
                .put("directors", new JSONArray());

        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList());
        MovieEntity updatedMovieEntity = new MovieEntity("Tangled", LocalDate.parse("2011-02-04"), "Animation", 100, null, null);
        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));
        when(movieRepository.save(updatedMovieEntity)).thenReturn(updatedMovieEntity);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movie.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("name").value("Tangled"));
    }


    @Test
    public void deleteMovie() throws Exception {
        MovieEntity movieEntity = new MovieEntity("Enredados", LocalDate.parse("2011-02-04"), "Animation", 100, Arrays.asList(), Arrays.asList());

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(delete("/movies/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNoContent());
    }


    @Test
    public void forbiddenDeletion() throws Exception {
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(delete("/movies/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }


    @Test
    public void forbiddenCreation() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Enredados")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("directors", new JSONArray())
                .put("actors", new JSONArray());
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void forbiddenUpdate() throws Exception {
        JSONObject movie = new JSONObject()
                .put("name", "Tangled")
                .put("releaseDate", "2011-02-04")
                .put("genre", "Animation")
                .put("duration", "100")
                .put("actors", new JSONArray())
                .put("directors", new JSONArray());
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movie.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }
}

package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import com.github.anaabad.ilovemovies.services.ActorService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {ActorService.class})
public class DirectorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private MovieRepository movieRepository;


    @Test
    public void getDirector() throws Exception {
        DirectorEntity directorEntity = new DirectorEntity("Steven Spielberg", "American", LocalDate.parse("1946-12-18"), null);
        when(directorRepository.findById(1L)).thenReturn(Optional.of(directorEntity));
        mockMvc.perform(get("/directors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Steven Spielberg"));
    }

    @Test
    public void getNonExistingDirector() throws Exception {
        mockMvc.perform(get("/directors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDirectors() throws Exception {
        List<DirectorEntity> directors = Arrays.asList(new DirectorEntity("Steven Spielberg", "American", LocalDate.parse("1946-12-18"), null),
                new DirectorEntity("Martin Scorcesse", "American", LocalDate.parse("1942-11-17"), null),
                new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), null),
                new DirectorEntity("Quentin Tarantino", "American", LocalDate.parse("1963-03-27"), null));

        when(directorRepository.findAll()).thenReturn(directors);

        mockMvc.perform(get("/directors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Steven Spielberg"))
                .andExpect(jsonPath("$[1].nationality").value("American"))
                .andExpect(jsonPath("$[2].birthDate").value("1954-08-16"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void updateDirector() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "James Cameron")
                .put("birthDate", "1954-08-16")
                .put("nationality", "American");

        DirectorEntity directorEntity = new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), null);

        when(directorRepository.findById(1L)).thenReturn(Optional.of(directorEntity));
        when(directorRepository.save(directorEntity)).thenReturn(directorEntity);

        mockMvc.perform(put("/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("name").value("James Cameron"));

    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void postDirector() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "Guy Ritchie")
                .put("nationality", "British")
                .put("birthDate", "1968-09-10");

        DirectorEntity directorEntity = new DirectorEntity("Guy Ritchie", "British", LocalDate.parse("1968-09-10"), null);

        when(directorRepository.save(directorEntity)).thenReturn(directorEntity);

        mockMvc.perform(post("/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Guy Ritchie"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void deleteDirector() throws Exception{

        mockMvc.perform(delete("/directors/1"))
                .andExpect(status().isNoContent());

        verify(directorRepository).deleteById(1L);
    }

    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenDeletion() throws Exception {
        mockMvc.perform(delete("/directors/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenCreation() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Guy Ritchie")
                .put("nationality", "British")
                .put("birthDate", "1968-09-10");

        mockMvc.perform(post("/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void forbiddenUpdate() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "James Cameron")
                .put("birthDate", "1954-08-16")
                .put("nationality", "American");

        mockMvc.perform(put("/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isForbidden());

    }
}

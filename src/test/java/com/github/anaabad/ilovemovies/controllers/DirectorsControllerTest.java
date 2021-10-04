package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.*;
import com.github.anaabad.ilovemovies.security.JwtAuthenticationEntryPoint;
import com.github.anaabad.ilovemovies.services.ActorService;
import com.github.anaabad.ilovemovies.services.AuthenticationService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {ActorService.class, AuthenticationService.class, JwtAuthenticationEntryPoint.class})
public class DirectorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmEuYWJhZGFycmFuekBnbWFpbC5jb20iLCJleHAiOjE5MTcwMzMzNDYsImlhdCI6MTUxNjIzOTAyMn0.W-VORCCxDUHT4_rLVGfu95rczq3k5g8nPabB-sFklhc";

    @Test
    public void getDirector() throws Exception {
        DirectorEntity directorEntity = new DirectorEntity("Steven Spielberg", "American", LocalDate.parse("1946-12-18"), null);
        when(directorRepository.findById(1L)).thenReturn(Optional.of(directorEntity));
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/directors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Steven Spielberg"));
    }

    @Test
    public void getNonExistingDirector() throws Exception {
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/directors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDirectors() throws Exception {
        List<DirectorEntity> directors = Arrays.asList(new DirectorEntity("Steven Spielberg", "American", LocalDate.parse("1946-12-18"), null),
                new DirectorEntity("Martin Scorcesse", "American", LocalDate.parse("1942-11-17"), null),
                new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), null),
                new DirectorEntity("Quentin Tarantino", "American", LocalDate.parse("1963-03-27"), null));

        when(directorRepository.findAll()).thenReturn(directors);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/directors")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Steven Spielberg"))
                .andExpect(jsonPath("$[1].nationality").value("American"))
                .andExpect(jsonPath("$[2].birthDate").value("1954-08-16"));
    }

    @Test
    public void updateDirector() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "James Cameron")
                .put("birthDate", "1954-08-16")
                .put("nationality", "American");

        DirectorEntity directorEntity = new DirectorEntity("James Cameron", "American", LocalDate.parse("1954-08-16"), null);

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(directorRepository.findById(1L)).thenReturn(Optional.of(directorEntity));
        when(directorRepository.save(directorEntity)).thenReturn(directorEntity);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(put("/directors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("name").value("James Cameron"));

    }

    @Test
    public void postDirector() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "Guy Ritchie")
                .put("nationality", "British")
                .put("birthDate", "1968-09-10");

        DirectorEntity directorEntity = new DirectorEntity("Guy Ritchie", "British", LocalDate.parse("1968-09-10"), null);

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(directorRepository.save(directorEntity)).thenReturn(directorEntity);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(post("/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Guy Ritchie"));
    }

    @Test
    public void deleteDirector() throws Exception {
        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(delete("/directors/1").header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNoContent());

        verify(directorRepository).deleteById(1L);
    }

    @Test
    public void forbiddenDeletion() throws Exception {

        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(delete("/directors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void forbiddenCreation() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Guy Ritchie")
                .put("nationality", "British")
                .put("birthDate", "1968-09-10");

        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(post("/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void forbiddenUpdate() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "James Cameron")
                .put("birthDate", "1954-08-16")
                .put("nationality", "American");

        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(put("/directors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());

    }
}

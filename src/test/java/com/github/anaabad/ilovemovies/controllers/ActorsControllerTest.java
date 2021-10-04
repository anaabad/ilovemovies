package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.*;
import com.github.anaabad.ilovemovies.security.JwtAuthenticationEntryPoint;
import com.github.anaabad.ilovemovies.services.AuthenticationService;
import com.github.anaabad.ilovemovies.services.DirectorService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {DirectorService.class, AuthenticationService.class, JwtAuthenticationEntryPoint.class})
@ActiveProfiles(profiles = "test")

public class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorRepository mockActorRepository;

    @MockBean
    private DirectorRepository mockDirectorRepository;

    @MockBean
    private MovieRepository mockMovieRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmEuYWJhZGFycmFuekBnbWFpbC5jb20iLCJleHAiOjE5MTcwMzMzNDYsImlhdCI6MTUxNjIzOTAyMn0.W-VORCCxDUHT4_rLVGfu95rczq3k5g8nPabB-sFklhc";

    @Test
    public void getActor() throws Exception {
        ActorEntity actor = new ActorEntity();
        actor.setName("Brad Pitt");
        when(mockActorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/actors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Brad Pitt"));

    }

    @Test
    public void getNonExistingActor() throws Exception {
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/actors/3")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getActors() throws Exception {
        List<MovieEntity> movies = new ArrayList<>();
        List<ActorEntity> actors = Arrays.asList(new ActorEntity("Brad Pitt", LocalDate.parse("1963-12-18"), "American", movies),
                new ActorEntity("Harrison Ford", LocalDate.parse("1942-07-13"), "American", movies),
                new ActorEntity("Emma Watson", LocalDate.parse("1990-04-15"), "British", movies),
                new ActorEntity("Nicole Kidman", LocalDate.parse("1967-05-20"), "Australian", movies));
        when(mockActorRepository.findAll()).thenReturn(actors);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(get("/actors")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[1].name").value("Harrison Ford"))
                .andExpect(jsonPath("$[2].nationality").value("British"))
                .andExpect(jsonPath("$[3].birthDate").value("1967-05-20"));
    }

    @Test
    public void createActor() throws Exception {

        ActorEntity actor = new ActorEntity("Chris Evans", LocalDate.parse("1981-05-13"), "American", null);
        JSONObject content = new JSONObject()
                .put("name", "Chris Evans")
                .put("birthDate", "1981-05-13")
                .put("nationality", "American")
                .put("movies", new JSONArray());

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(mockActorRepository.save(actor)).thenReturn(actor);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Chris Evans"))
                .andExpect(jsonPath("birthDate").value("1981-05-13"))
                .andExpect(jsonPath("nationality").value("American"));
    }

    @Test
    public void updateActor() throws Exception {

        JSONObject content = new JSONObject()
                .put("name", "Chris Evans")
                .put("birthDate", "1981-05-13")
                .put("nationality", "American")
                .put("movies", new JSONArray());

        ActorEntity actor = new ActorEntity("Chris Evans", LocalDate.parse("1981-05-13"), "American", null);

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(mockActorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(mockActorRepository.save(actor)).thenReturn(actor);
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(put("/actors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("name").value("Chris Evans"));

    }

    @Test
    public void deleteActor() throws Exception {
        ActorEntity actorEntity = new ActorEntity("Chris Evans", LocalDate.parse("1981-05-13"), "American", null);

        RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");

        when(mockActorRepository.findById(1L)).thenReturn(Optional.of(actorEntity));
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList(roleEntity))));

        mockMvc.perform(delete("/actors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNoContent());
    }

    @Test
    public void forbiddenDeletion() throws Exception {
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(delete("/actors/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void forbiddenUpdate() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Chris Evans")
                .put("birthDate", "1981-05-13")
                .put("nationality", "American")
                .put("movies", new JSONArray());
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(put("/actors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void forbiddenCreation() throws Exception {
        JSONObject content = new JSONObject()
                .put("name", "Chris Evans")
                .put("birthDate", "1981-05-13")
                .put("nationality", "American")
                .put("movies", new JSONArray());
        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies", Arrays.asList())));

        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content.toString())
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }
}

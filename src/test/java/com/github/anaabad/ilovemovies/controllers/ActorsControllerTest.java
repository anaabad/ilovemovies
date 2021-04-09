package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.DirectorRepository;
import com.github.anaabad.ilovemovies.services.DirectorService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
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
@ComponentScan(basePackageClasses = {DirectorService.class})
public class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorRepository mockActorRepository;

    @MockBean
    private DirectorRepository mockDirectorRepository;

    @Test
    public void getActor() throws Exception {
        ActorEntity actor = new ActorEntity();
        actor.setName("Brad Pitt");
        when(mockActorRepository.findById(1L)).thenReturn(Optional.of(actor));

        mockMvc.perform(get("/actors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Brad Pitt"));
    }

    @Test
    public void getNonExistingActor() throws Exception {

        mockMvc.perform(get("/actors/3"))
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

        mockMvc.perform(get("/actors"))
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

        when(mockActorRepository.save(actor)).thenReturn(actor);

        mockMvc.perform(post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Chris Evans"))
                .andExpect(jsonPath("birthDate").value("1981-05-13"))
                .andExpect(jsonPath("nationality").value("American"));
    }

    @Test
    public void deleteActor() throws Exception {
        ActorEntity actorEntity = new ActorEntity("Chris Evans", LocalDate.parse("1981-05-13"), "American", null);

        when(mockActorRepository.findById(1L)).thenReturn(Optional.of(actorEntity));
        mockMvc.perform(delete("/actors/1"))
                .andExpect(status().isNoContent());
    }
}

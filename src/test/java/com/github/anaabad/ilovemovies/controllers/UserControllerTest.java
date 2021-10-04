package com.github.anaabad.ilovemovies.controllers;

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
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ComponentScan(basePackageClasses = {AuthenticationService.class, JwtAuthenticationEntryPoint.class})
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private RoleRepository roleRepository;

    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmEuYWJhZGFycmFuekBnbWFpbC5jb20iLCJleHAiOjE5MTcwMzMzNDYsImlhdCI6MTUxNjIzOTAyMn0.W-VORCCxDUHT4_rLVGfu95rczq3k5g8nPabB-sFklhc";


    @Test
    public void createUserSuccessfully() throws Exception {
        JSONObject user = new JSONObject()
                .put("email", "ana.abadarranz@gmail.com")
                .put("password", "ilovemovies!");

        RoleEntity roleEntity = new RoleEntity("ROLE_USER");
        UserEntity userEntity = new UserEntity("ana.abadarranz@gmail.com", "$2a$10$RRgjaXe5DOSHrVV900lEP.tKqIVilyAq6kJ2zHebRyZz3v7pK/ySW", Arrays.asList(roleEntity));
        String content = user.toString();

        when(userRepository.findByEmail("ana.abadarranz@gmail.com")).thenReturn(Optional.of(new UserEntity("ana.abadarranz@gmail.com", "ilovemovies!", Arrays.asList(roleEntity))));
        when(userRepository.save(any())).thenReturn(userEntity);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleEntity));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isCreated());
    }
}

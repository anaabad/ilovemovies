package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.*;
import com.github.anaabad.ilovemovies.security.JwtAuthenticationEntryPoint;
import com.github.anaabad.ilovemovies.services.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@ComponentScan(basePackageClasses = {AuthenticationService.class, JwtAuthenticationEntryPoint.class})
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActorRepository actorRepository;

    @MockBean
    DirectorRepository directorRepository;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void authenticationTest() throws Exception {

        JSONObject user = new JSONObject()
                .put("email", "ana.abad@gmail.com")
                .put("password", "iLoveMovies");
        RoleEntity roleEntity = new RoleEntity("ROLE_ENTITY");
        UserEntity userEntity = new UserEntity("ana.abad@gmail.com", passwordEncoder.encode("iLoveMovies"), List.of(roleEntity));

        when(userRepository.findByEmail("ana.abad@gmail.com")).thenReturn(java.util.Optional.of(userEntity));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toString()))
                .andExpect(status().isAccepted());
    }

}

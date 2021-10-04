package com.github.anaabad.ilovemovies.controllers;

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
import org.springframework.test.web.servlet.MockMvc;

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


    @Test
    public void authenticationTest() throws Exception {

        JSONObject user = new JSONObject()
                .put("email", "ana.abad@gmail.com")
                .put("password", "iLoveMovies");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuYWJhZEBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTYzMTA5NjU4MSwiaXNzIjoiZ2FtZXByb2ZpbGUiLCJleHAiOjE2MzEwOTY1ODJ9.LjW6iyZUKWz4cQHCr0uqFNAVHeF0hv7sGhLZAttkvfc"));
    }

}

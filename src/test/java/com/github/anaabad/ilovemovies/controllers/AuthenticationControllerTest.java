package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.services.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthenticationService authenticationService;

    @Test
    public void authenticationTest() throws Exception {

        JSONObject user = new JSONObject()
                .put("email" , "ana.abad@gmail.com")
                .put("password", "iLoveMovies");

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuYWJhZEBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTYzMTA5NjU4MSwiaXNzIjoiZ2FtZXByb2ZpbGUiLCJleHAiOjE2MzEwOTY1ODJ9.LjW6iyZUKWz4cQHCr0uqFNAVHeF0hv7sGhLZAttkvfc";
        when(authenticationService.authenticateUser("ana.abad@gmail.com", "iLoveMovies")).thenReturn(token);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuYWJhZEBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTYzMTA5NjU4MSwiaXNzIjoiZ2FtZXByb2ZpbGUiLCJleHAiOjE2MzEwOTY1ODJ9.LjW6iyZUKWz4cQHCr0uqFNAVHeF0hv7sGhLZAttkvfc"));
    }

}

package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.services.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private final String VALID_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmEuYWJhZGFycmFuekBnbWFpbC5jb20iLCJleHAiOjE5MTcwMzMzNDYsImlhdCI6MTUxNjIzOTAyMn0.kU2JuUuLmt0P2ujDXwKff0QG6QO4_HNNZ6LSdXWg0rE";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secret", "Test");
    }

    @Test
    void createToken(){
//        RoleEntity role = new RoleEntity();
//        UserEntity userEntity = new UserEntity("ana.abadarranz@gmail.com", "iLoveMovies", Arrays.asList(role));
//        String token = jwtService.createToken(userEntity);
//        assertThat(token).isNotNull();
//        assertThat(jwtService.getClaimsFromToken(token).getSubject()).isEqualTo("ana.abadarranz@gmail.com");
    }

    @Test
    void getEmailFromToken(){
        Claims claimsFromToken = jwtService.getClaimsFromToken(VALID_JWT);
        assertThat(claimsFromToken.getSubject()).isEqualTo("ana.abadarranz@gmail.com");
    }
}

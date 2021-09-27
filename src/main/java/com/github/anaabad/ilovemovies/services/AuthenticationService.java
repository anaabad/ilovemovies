package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


    public String authenticateUser(String email, String password){

        authenticate(email, password);
        UserEntity user = userService.findByEmail(email).orElseThrow();
        return jwtService.createToken(user);
    }

    public Authentication authenticate(String username, String password){
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}

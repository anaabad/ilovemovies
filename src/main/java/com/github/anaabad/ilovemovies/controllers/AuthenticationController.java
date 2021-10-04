package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(@RequestBody UserDto userDto) {
        return authenticationService.authenticateUser(userDto.email, userDto.password);
    }

}

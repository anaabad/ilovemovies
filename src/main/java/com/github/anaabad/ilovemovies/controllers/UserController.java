package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.services.RoleService;
import com.github.anaabad.ilovemovies.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto user) {
        userService.create(user);
    }
}

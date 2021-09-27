package com.github.anaabad.ilovemovies.controllers.dtos;

import lombok.Data;

@Data
public class UserDto {

    public String email;
    public String password;
    public String roles;
}

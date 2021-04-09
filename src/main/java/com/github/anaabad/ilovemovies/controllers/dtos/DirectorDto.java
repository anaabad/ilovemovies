package com.github.anaabad.ilovemovies.controllers.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DirectorDto {

    private String name;
    private String nationality;
    private LocalDate birthDate;
    private List<MovieDto> movies;
}

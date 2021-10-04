package com.github.anaabad.ilovemovies.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

    private String name;
    private String nationality;
    private LocalDate birthDate;
    private List<MovieDto> movies;
}

package com.github.anaabad.ilovemovies.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActorDto {

    private String name;
    private LocalDate birthDate;
    private String nationality;
    private List<MovieDto> movies;
}

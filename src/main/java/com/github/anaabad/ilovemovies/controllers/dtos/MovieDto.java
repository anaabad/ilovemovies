package com.github.anaabad.ilovemovies.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private String name;
    private LocalDate releaseDate;
    private String genre;
    private Integer duration;
    private List<DirectorDto> directors;
    private List<ActorDto> actors;
}

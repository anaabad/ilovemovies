package com.github.anaabad.ilovemovies.controllers.dtos;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActorDto {

    private String name;
    private Date birthDate;
    private String nationality;
    private List<MovieDto> movies;
}

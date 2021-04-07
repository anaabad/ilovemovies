package com.github.anaabad.ilovemovies.controllers.dtos;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DirectorDto {

    private String name;
    private String nationality;
    private Date birthDate;
    private List<MovieEntity> movies;
}

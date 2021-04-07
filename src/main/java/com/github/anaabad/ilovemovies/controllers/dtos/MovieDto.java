package com.github.anaabad.ilovemovies.controllers.dtos;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieDto {

    private String name;
    private Date releaseDate;
    private String genre;
    private Integer duration;
    private List<DirectorEntity> directors;
    private List<ActorEntity> actors;
}

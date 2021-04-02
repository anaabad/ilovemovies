package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;
@Data
@Entity
public class ActorEntity extends AbstractEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Date birth_date;
    @Column(nullable = false)
    private String nacionality;
    @ManyToMany(mappedBy = "actors")
    private List<MovieEntity> movies;


}

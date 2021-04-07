package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class DirectorEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private Date birthDate;
    @ManyToMany(mappedBy = "directors")
    private List<MovieEntity> movies;
}

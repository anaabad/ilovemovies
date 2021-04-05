package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class MovieEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Date releaseDate;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private Integer duration;
    @ManyToMany
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id",
                    referencedColumnName = "id"))
    private List<DirectorEntity> directors;
    @ManyToMany
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private List<ActorEntity> actors;
}

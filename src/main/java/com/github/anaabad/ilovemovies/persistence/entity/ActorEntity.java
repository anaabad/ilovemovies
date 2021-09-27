package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actor")
public class ActorEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String nationality;
    @ManyToMany(mappedBy = "actors")
    private List<MovieEntity> movies;
}

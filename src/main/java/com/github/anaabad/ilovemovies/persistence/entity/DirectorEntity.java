package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "director")
@EqualsAndHashCode(callSuper=false)
public class DirectorEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private LocalDate birthDate;
    @ManyToMany(mappedBy = "directors")
    private List<MovieEntity> movies;
}

package com.github.anaabad.ilovemovies.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Entity
public class DirectorEntity extends AbstractEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private Date birth_Date;
    @ManyToMany(mappedBy = "directors")
    private List<DirectorEntity> directors;
}

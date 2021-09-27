package com.github.anaabad.ilovemovies.persistence.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    protected Long id;

    @Version
    protected int version;

    @NotNull
    @Column(name = "created_at")
    protected LocalDate createdAt;

    @NotNull
    @Column(name = "modified_at")
    protected LocalDate modifiedAt;
}

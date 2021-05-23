package com.github.anaabad.ilovemovies.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity extends AbstractEntity{

    @Column
    private String text;
    @ManyToOne
    @JoinColumn(name="movie_id")
    private MovieEntity movie;

}

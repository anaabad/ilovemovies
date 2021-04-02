package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    ActorEntity getById(@Param("id") Long id);
    List<ActorEntity> getAll();

}

package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {


}

package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {

}

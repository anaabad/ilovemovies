package com.github.anaabad.ilovemovies.persistence.repository;

import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}

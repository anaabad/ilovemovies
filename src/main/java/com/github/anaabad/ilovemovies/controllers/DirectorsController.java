package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.DirectorEntity;
import com.github.anaabad.ilovemovies.services.DirectorService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/directors")
public class DirectorsController {

    DirectorService directorService;

    public DirectorsController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<DirectorEntity> show(@PathVariable Long id) {
        return directorService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DirectorEntity> index() {
        return directorService.getAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    DirectorEntity update(@PathVariable Long id, @RequestBody DirectorEntity directorEntity) throws NotFoundException {
        Optional<DirectorEntity> directorEntityOpt = directorService.findById(id);
        if (directorEntityOpt.isPresent()) {
            DirectorEntity director = directorEntityOpt.get();
            director.setName(directorEntity.getName());
            director.setBirth_Date(directorEntity.getBirth_Date());
            director.setNationality(directorEntity.getNationality());
            return directorService.save(director);
        } else {
            throw new NotFoundException("Director not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DirectorEntity create(DirectorEntity directorEntity) {
        return directorService.save(directorEntity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(Long id) {
        directorService.delete(id);
    }

}

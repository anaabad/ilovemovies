package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.services.ActorService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorsController {

    ActorService actorService;

    public ActorsController(ActorService actorService) {
        this.actorService = actorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<ActorEntity> show(@PathVariable Long id) {
        return actorService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ActorEntity> index() {
        return actorService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ActorEntity create(@RequestBody ActorEntity actor) {
        return actorService.save(actor);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public ActorEntity update(@RequestBody ActorEntity updatedActor, @PathVariable Long id) throws NotFoundException {
        Optional<ActorEntity> actorOpt = actorService.findById(id);
        if (actorOpt.isPresent()) {
            ActorEntity actor = actorOpt.get();
            actor.setName(updatedActor.getName());
            actor.setNationality((updatedActor.getNationality()));
            actor.setBirth_date(updatedActor.getBirth_date());
            return actorService.save(actor);
        } else {
            throw new NotFoundException("Actor not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@RequestParam(value = "id") Long id) {
        Optional<ActorEntity> actorOpt = actorService.findById(id);
        actorOpt.ifPresent(value -> actorService.delete(value));
    }
}

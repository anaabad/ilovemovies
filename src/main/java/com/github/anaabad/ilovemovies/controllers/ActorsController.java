package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.services.ActorService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorsController {

    private final ActorService actorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<ActorDto> show(@PathVariable Long id) {
        return actorService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ActorDto> index() {
        return actorService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ActorDto create(@RequestBody ActorDto actor) {
        return actorService.save(actor);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public ActorDto update(@RequestBody ActorDto updatedActor, @PathVariable Long id) throws NotFoundException {
        Optional<ActorDto> actorOpt = actorService.findById(id);
        if (actorOpt.isPresent()) {
            ActorDto actor = actorOpt.get();
            actor.setName(updatedActor.getName());
            actor.setNationality((updatedActor.getNationality()));
            actor.setBirthDate(updatedActor.getBirthDate());
            return actorService.save(actor);
        } else {
            throw new NotFoundException("Actor not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@RequestParam(value = "id") Long id) {
        Optional<ActorDto> actorOpt = actorService.findById(id);
        actorOpt.ifPresent(value -> actorService.delete(value));
    }
}

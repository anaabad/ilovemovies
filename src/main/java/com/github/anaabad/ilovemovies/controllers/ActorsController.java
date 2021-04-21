package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.services.ActorService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorsController {

    private final ActorService actorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ActorDto show(@PathVariable Long id) {
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
        return actorService.update(id, updatedActor);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ActorDto actor = actorService.findById(id);
        actorService.delete(actor);
    }
}

package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.ActorDto;
import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.services.ActorService;
import com.github.anaabad.ilovemovies.services.MovieService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorsController {

    private final ActorService actorService;
    private final MovieService movieService;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ActorDto create(@RequestBody ActorDto actor) {
        return actorService.save(actor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    public ActorDto update(@RequestBody ActorDto updatedActor, @PathVariable Long id) throws NotFoundException {
        return actorService.update(id, updatedActor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ActorDto actor = actorService.findById(id);
        actorService.delete(actor);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teenagers")
    public List<ActorDto> teenagers() {
        return actorService.getAll()
                .stream()
                .filter(actorDto ->
                        actorDto.getBirthDate()
                                .isAfter(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}/kind/{type}")
    public List<MovieDto> moviesOfType(@PathVariable Long id, @PathVariable String type) {
        return movieService.findByActor(id)
                .stream()
                .filter(movieDto -> Objects.equals(movieDto.getGenre(), type))
                .collect(Collectors.toList());
    }
}

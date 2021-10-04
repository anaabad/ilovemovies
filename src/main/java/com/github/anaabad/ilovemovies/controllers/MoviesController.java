package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.services.MovieService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieDto> index() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDto show(@PathVariable Long id) throws NotFoundException {
        return movieService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movieDto) {
        return movieService.save(movieDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieDto update(@PathVariable Long id, @RequestBody MovieDto movieDto) throws NotFoundException {

        return movieService.update(id, movieDto);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws NotFoundException {
        MovieDto movie = movieService.getById(id);
        movieService.delete(movie);
    }
}

package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.services.MovieService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movieDto){
        return movieService.save(movieDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieDto update(@PathVariable Long id, @RequestBody MovieDto movieDto) throws NotFoundException{
        MovieDto movie = movieService.getById(id);

        movie.setName(movieDto.getName());
        movie.setDuration(movieDto.getDuration());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setActors(movieDto.getActors());
        movie.setDirectors(movieDto.getDirectors());
        return movieService.save(movie);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws NotFoundException {
        MovieDto movie = movieService.getById(id);
        movieService.delete(movie);
    }
}

package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
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
    public List<MovieEntity> index() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<MovieEntity> show(@PathVariable Long id){
        return movieService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieEntity create(@RequestBody MovieEntity movieEntity){
        return movieService.save(movieEntity);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieEntity update(@PathVariable Long id, @RequestBody MovieEntity movieEntity) throws NotFoundException{
        Optional<MovieEntity> movieEntityOpt = movieService.getById(id);

        if(movieEntityOpt.isPresent()){
            MovieEntity movie = movieEntityOpt.get();
            movie.setName(movieEntity.getName());
            movie.setDuration(movieEntity.getDuration());
            movie.setGenre(movieEntity.getGenre());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setActors(movieEntity.getActors());
            movie.setDirectors(movieEntity.getDirectors());
            return movieService.save(movie);
        }else{
            throw new NotFoundException("Movie not Found");
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Optional<MovieEntity> movie = movieService.getById(id);
        movie.ifPresent(value -> movieService.delete(value));
    }
}

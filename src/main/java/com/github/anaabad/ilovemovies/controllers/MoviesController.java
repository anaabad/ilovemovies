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
    public Optional<MovieDto> show(@PathVariable Long id){
        return movieService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movieDto){
        return movieService.save(movieDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieDto update(@PathVariable Long id, @RequestBody MovieDto movieDto) throws NotFoundException{
        Optional<MovieDto> movieDtoOpt = movieService.getById(id);

        if(movieDtoOpt.isPresent()){
            MovieDto movie = movieDtoOpt.get();
            movie.setName(movieDto.getName());
            movie.setDuration(movieDto.getDuration());
            movie.setGenre(movieDto.getGenre());
            movie.setReleaseDate(movieDto.getReleaseDate());
            movie.setActors(movieDto.getActors());
            movie.setDirectors(movieDto.getDirectors());
            return movieService.save(movie);
        }else{
            throw new NotFoundException("Movie not Found");
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Optional<MovieDto> movie = movieService.getById(id);
        movie.ifPresent(value -> movieService.delete(value));
    }
}

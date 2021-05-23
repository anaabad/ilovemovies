package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.CommentDto;
import com.github.anaabad.ilovemovies.controllers.dtos.MovieDto;
import com.github.anaabad.ilovemovies.services.CommentService;
import com.github.anaabad.ilovemovies.services.MovieService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    MovieService movieService;
    CommentService commentService;

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

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movieDto){
        return movieService.save(movieDto);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieDto update(@PathVariable Long id, @RequestBody MovieDto movieDto) throws NotFoundException{

        return movieService.update(id, movieDto);

    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws NotFoundException {
        MovieDto movie = movieService.getById(id);
        movieService.delete(movie);
    }

    @PostMapping("{id}/add_comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto add_comment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws NotFoundException {
        commentDto.setMovie(movieService.getById(id));
        return commentService.save(commentDto);
    }

    @GetMapping("{id}/comments")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CommentDto> get_comments(@PathVariable Long id){
        return commentService.getAllByMovieId(id);
    }
}

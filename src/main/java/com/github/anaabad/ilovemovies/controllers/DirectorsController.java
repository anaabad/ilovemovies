package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.services.DirectorService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorsController {

    private final DirectorService directorService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DirectorDto show(@PathVariable Long id) {
        return directorService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DirectorDto> index() {
        return directorService.getAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    DirectorDto update(@PathVariable Long id, @RequestBody DirectorDto directorDto) throws NotFoundException {

        return directorService.update(id, directorDto);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DirectorDto create(@RequestBody DirectorDto directorDto) {
        return directorService.save(directorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        directorService.delete(id);
    }

}

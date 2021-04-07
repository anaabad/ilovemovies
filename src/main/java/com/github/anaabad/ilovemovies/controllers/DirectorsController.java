package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.controllers.dtos.DirectorDto;
import com.github.anaabad.ilovemovies.services.DirectorService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorsController {

    private final DirectorService directorService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<DirectorDto> show(@PathVariable Long id) {
        return directorService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DirectorDto> index() {
        return directorService.getAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    DirectorDto update(@PathVariable Long id, @RequestBody DirectorDto directorDto) throws NotFoundException {
        Optional<DirectorDto> directorDtoOpt = directorService.findById(id);
        if (directorDtoOpt.isPresent()) {
            DirectorDto director = directorDtoOpt.get();
            director.setName(directorDto.getName());
            director.setBirthDate(directorDto.getBirthDate());
            director.setNationality(directorDto.getNationality());
            return directorService.save(director);
        } else {
            throw new NotFoundException("Director not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DirectorDto create(DirectorDto directorDto) {
        return directorService.save(directorDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(Long id) {
        directorService.delete(id);
    }

}

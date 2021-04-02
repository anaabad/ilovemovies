package com.github.anaabad.ilovemovies.controllers;

import com.github.anaabad.ilovemovies.persistence.entity.ActorEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorsController {

    ActorRepository actorRepository;

    public ActorsController(ActorRepository repository){
        this.actorRepository = repository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ActorEntity show(@PathVariable Long id){
        return actorRepository.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ActorEntity> index(){
        return actorRepository.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ActorEntity create(@RequestBody ActorEntity actor){
        return actorRepository.save(actor);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public ActorEntity update(@RequestBody ActorEntity updatedActor, @PathVariable Long id) throws NotFoundException {
        Optional<ActorEntity> actorOpt = actorRepository.findById(id);
        if(actorOpt.isPresent()){
            ActorEntity actor = actorOpt.get();
            actor.setName(updatedActor.getName());
            actor.setNacionality((updatedActor.getNacionality()));
            actor.setBirth_date(updatedActor.getBirth_date());
            return actorRepository.save(actor);
        } else {
            throw new NotFoundException("Actor not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@RequestParam(value = "id") Long id){
        Optional<ActorEntity> actorOpt = actorRepository.findById(id);
        actorOpt.ifPresent(value -> actorRepository.delete(value));
    }
}

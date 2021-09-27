package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.ActorRepository;
import com.github.anaabad.ilovemovies.persistence.repository.UserRepository;
import com.github.anaabad.ilovemovies.services.transformers.UserTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTrf userTrf;

    public UserDto save(UserDto userDto){
        return userTrf.userEntityToDto(userRepository.save(userTrf.userDtoToEntity(userDto)));
    }

    public Optional<UserEntity> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}

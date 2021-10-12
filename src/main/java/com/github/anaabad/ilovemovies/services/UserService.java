package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.UserRepository;
import com.github.anaabad.ilovemovies.services.transformers.UserTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserTrf userTrf;

    public UserDto create(UserDto userDto) {
        final UserEntity userEntity = userTrf.userDtoToEntity(userDto);
        userEntity.setRoles(Collections.singletonList(roleService.findByName("ROLE_USER").get()));
        return userTrf.userEntityToDto(userRepository.save(userEntity));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

package com.github.anaabad.ilovemovies.services.transformers;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.persistence.repository.RoleRepository;
import com.github.anaabad.ilovemovies.services.ActorService;
import com.github.anaabad.ilovemovies.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserTrf {

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserDto userEntityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        if (userEntity.getRoles() != null) {
            userDto.setRoles(String.join(",", userEntity.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toList())));
        }
        return userDto;
    }

    public UserEntity userDtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());

        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoles() != null) {

            userEntity.setRoles(new ArrayList<>());
            userEntity.getRoles()
                    .addAll(Arrays.stream(userDto.getRoles().split(","))
                            .map(roleService::findByName)
                            .map(Optional::get).collect(Collectors.toList()));

        }
        return userEntity;
    }
}

package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTrf {

    public UserDto userEntityToDto(UserEntity userEntity){
        UserDto userDto =new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        return userDto;
    }

    public UserEntity userDtoToEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userEntity;
    }
}

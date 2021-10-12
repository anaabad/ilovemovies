package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.UserDto;
import com.github.anaabad.ilovemovies.persistence.entity.RoleEntity;
import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import com.github.anaabad.ilovemovies.services.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTrfTest {

    @InjectMocks
    UserTrf cut;

    @Mock
    RoleService roleService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void userDtoToUserEntity() {
        RoleEntity roleEntity = new RoleEntity("ROLE_USER");
        UserDto userDto = new UserDto("ana.abadarranz@gmail.com", "iLoveMovies!", "ROLE_USER");
        UserEntity userEntity = new UserEntity("ana.abadarranz@gmail.com", "iLoveMovies!", Arrays.asList(roleEntity));

        when(roleService.findByName("ROLE_USER")).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode("iLoveMovies!")).thenReturn("iLoveMovies!");

        assertThat(cut.userDtoToEntity(userDto)).isEqualToComparingFieldByField(userEntity);
    }

    @Test
    void userEntityToUserDto() {
        RoleEntity roleEntity = new RoleEntity("ROLE_USER");
        UserDto userDto = new UserDto("ana.abadarranz@gmail.com", "iLoveMovies!", "ROLE_USER");
        UserEntity userEntity = new UserEntity("ana.abadarranz@gmail.com", "iLoveMovies!", Arrays.asList(roleEntity));

        assertThat(cut.userEntityToDto(userEntity)).isEqualToComparingFieldByField(userDto);
    }
}

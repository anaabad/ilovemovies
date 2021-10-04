package com.github.anaabad.ilovemovies.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;


    @Test
    public void findByName(){
             roleService.findByName("ROLE_USER");

    }
}

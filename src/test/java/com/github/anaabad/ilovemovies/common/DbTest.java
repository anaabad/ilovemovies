package com.github.anaabad.ilovemovies.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(properties = {"configuration.db.port=${POSTGRES_PORT}"})
@ExtendWith(PostgresExtension.class)
public @interface DbTest {
}

package com.github.anaabad.ilovemovies.common;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresExtension implements BeforeAllCallback, AfterAllCallback {

    static TestPostgresContainer container = new TestPostgresContainer();


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        container.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        container.start();
        System.setProperty("POSTGRES_PORT", String.valueOf(container.getMappedPort(5432)));
    }

    static class TestPostgresContainer extends PostgreSQLContainer<TestPostgresContainer> {
        TestPostgresContainer() {
            super("postgres:12.2");
            withDatabaseName("ilovemovies");
            withUsername("ilovemovies");
            withPassword("ilovemovies");
        }
    }
}

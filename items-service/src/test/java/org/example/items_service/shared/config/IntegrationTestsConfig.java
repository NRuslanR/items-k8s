package org.example.items_service.shared.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class IntegrationTestsConfig 
{
    private static PostgreSQLContainer<?> postgreSQLContainer =
        new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16.3-alpine")
        );

    static
    {
        postgreSQLContainer.start();

        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }
}

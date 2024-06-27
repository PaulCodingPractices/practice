package com.example.demo.dbTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public MySQLContainer<?> mySQLContainer() {
        MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.25")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");
        mysql.start();
        return mysql;
    }

    @Bean
    public void configureMySQLContainer(MySQLContainer<?> mysql) {
        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
        System.setProperty("spring.datasource.driver-class-name", mysql.getDriverClassName());
    }
}

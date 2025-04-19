package com.newspaper.newspaper.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseConfigTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testDatabaseIsH2() {
        String dbProductName = jdbcTemplate.queryForObject(
                "SELECT H2VERSION() FROM DUAL", String.class);
        assertThat(dbProductName).isNotNull();
    }
}

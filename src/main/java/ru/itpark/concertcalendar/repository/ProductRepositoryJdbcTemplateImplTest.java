package ru.itpark.concertcalendar.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // JUnit добавляет возможность взаимодействовать со Spring
@JdbcTest
class ProductRepositoryJdbcTemplateImplTest {
    @Autowired // DI в поле
    private JdbcTemplate jdbcTemplate;

    private ProductRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new ProductRepositoryJdbcTemplateImpl(jdbcTemplate);
    }

    @Test
    void first() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "products");
        int actual = repository.getAll().size();
        assertEquals(2, actual);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "products");
        int after =repository.getAll().size();
        assertEquals(0, after);
    }

    @Test
    void second() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "products");
        int actual = repository.getAll().size();
        assertEquals(2, actual);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "products");
        int after =repository.getAll().size();
        assertEquals(0, after);
    }
}
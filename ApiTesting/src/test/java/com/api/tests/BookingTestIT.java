package com.api.tests;

import com.api.builder.BookingBuilder;
import com.api.factory.BookingFactory;
import com.api.repository.BookingRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class BookingTestIT {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("tripstack_db")
                    .withUsername("root")
                    .withPassword("Cipi@0971");

    static BookingRepository repository;
    static BookingFactory factory;

    @BeforeAll
    static void setup() {

        Flyway.configure()
                .dataSource(
                        mysql.getJdbcUrl(),
                        mysql.getUsername(),
                        mysql.getPassword())
                .locations("classpath:db/migration")
                .load()
                .migrate();

        repository =
                new BookingRepository(
                        mysql.getJdbcUrl(),
                        mysql.getUsername(),
                        mysql.getPassword());

        factory =
                new BookingFactory(repository);
    }

    @BeforeEach
    void resetDatabase() {
        repository.resetTable();
    }

    @Test
    void shouldStartWithEmptyBookingsTable() {

        assertEquals(
                0,
                repository.countBookings());
    }

    @Test
    void shouldPersistBooking() {

        long id =
                factory.persisted();

        assertTrue(id > 0);

        assertEquals(
                1,
                repository.countBookings());
    }

    @Test
    void shouldPersistConfirmedBooking() {

        factory.persisted(
                BookingBuilder.newBooking()
                        .withState("CONFIRMED")
        );

        assertEquals(
                1,
                repository.countByState(
                        "CONFIRMED"));
    }

    @Test
    void shouldPersistRefundedBooking() {

        factory.persisted(
                BookingBuilder.newBooking()
                        .withState("REFUNDED")
        );

        assertEquals(
                1,
                repository.countByState(
                        "REFUNDED"));
    }

    @Test
    void shouldResetDataBetweenTests() {

        assertEquals(
                0,
                repository.countBookings());
    }
}
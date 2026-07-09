package com.example.Selenium.data;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.example.Selenium.data.OrderBuilder.anOrder;

@Testcontainers(disabledWithoutDocker = true)
public class OrdersDataIT {
    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("retail_test")
            .withUsername("trainer")
            .withPassword("trainer")
            .withStartupTimeout(Duration.ofMinutes(2));

    static OrderRepository repository;
    static OrderFactory factory;
    @BeforeAll
    static void migrateSchema(){
        Flyway.configure()
                .dataSource(postgres.getJdbcUrl(),postgres.getUsername(),postgres.getPassword())
                .locations("classpath:db/migration")
                .load()
                .migrate();

        repository=new OrderRepository(postgres.getJdbcUrl(),postgres.getUsername(),postgres.getPassword());
        factory=new OrderFactory(repository);
    }

    @BeforeEach
    void restMutableTables(){
        repository.resetMutableTables();
    }

    @Test
    void flywaySeedsReferenceDataButNotPerTestOrders(){
        assertEquals(4,repository.referenceStatusCount(),"Reference statuses are seeded by migration");
        assertEquals(0,repository.count(),"Per-test order rows should not come from migrations");
    }

    @Test
    void factoryPersistsBuilderDataAgainstIsolatedPostgres(){
        long id=factory.persisted(anOrder().withQuantity(3));
        assertTrue(id>0);
        assertEquals(1,repository.count());
    }
    @Test
    void countsOnlyThisTestsOrders(){
        factory.persisted(anOrder());
        factory.persisted(anOrder().withSku("SHU-RET-202").withQuantity(2));
        assertEquals(2,repository.count());
    }
    @Test
    void resetMakesTestOrderIndependent(){
        assertEquals(0,repository.count(),"Previous tests must not leaks rows into this test");
        factory.persisted(anOrder().refunded());
        assertEquals(1,repository.count());
        assertEquals(1,repository.countByStatus("REFUNDED"));
    }
}

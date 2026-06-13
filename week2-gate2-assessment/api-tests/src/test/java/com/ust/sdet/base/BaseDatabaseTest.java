package com.ust.sdet.base;
import com.ust.sdet.config.DatabaseConfig;
import com.ust.sdet.dbframework.support.DbSupport;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseDatabaseTest extends BaseTest {
    protected DbSupport db;
    @BeforeEach
    void setupDatabase()
            throws Exception {
        db = new DbSupport(DatabaseConfig.fromEnvironment());
    }


}
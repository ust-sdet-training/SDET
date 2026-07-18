package com.tripstack.db;

import com.tripstack.config.ConfigReader;
import com.tripstack.database.DatabaseValidator;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDBTest {

    @Test
    public void verifyUserExistsInDatabase() throws SQLException {

        assertTrue(
                DatabaseValidator.userExists(
                        ConfigReader.getEmployeeId()
                )
        );

    }

}
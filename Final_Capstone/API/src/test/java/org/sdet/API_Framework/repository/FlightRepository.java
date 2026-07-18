package org.sdet.API_Framework.repository;

import org.sdet.Database.database.DatabaseConnection;
import org.sdet.Database.database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FlightRepository {

    public boolean flightExists(String flightId) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(Queries.GET_FLIGHT);

            statement.setString(1, flightId);

            ResultSet result =
                    statement.executeQuery();

            return result.next();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
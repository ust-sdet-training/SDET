package database;

import java.sql.Connection;
import java.sql.Statement;


public class DatabaseSetup {


    public static void createTables() {


        String bookingTable = """
                
                CREATE TABLE IF NOT EXISTS bookings
                (
                    id VARCHAR(50) PRIMARY KEY,

                    journey_type VARCHAR(50),

                    inventory_id VARCHAR(100),

                    state VARCHAR(50),

                    refundable BOOLEAN,

                    pnr VARCHAR(50)

                );

                """;


        try {


            Connection connection =
                    DatabaseConnection.getConnection();


            Statement statement =
                    connection.createStatement();


            statement.execute(bookingTable);



            System.out.println(
                    "Database tables created successfully"
            );


        } catch(Exception e){


            throw new RuntimeException(
                    "Database setup failed",
                    e
            );

        }


    }

}
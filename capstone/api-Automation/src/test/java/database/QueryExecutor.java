package database;


import java.sql.*;


public class QueryExecutor {


    private Connection connection;


    public QueryExecutor(){

        connection =
                DatabaseConnection.getConnection();

    }



    public ResultSet executeQuery(String sql){


        try{


            Statement statement =
                    connection.createStatement();


            return statement.executeQuery(sql);



        }catch(Exception e){


            throw new RuntimeException(
                    "Query execution failed",
                    e
            );

        }


    }



    public int executeUpdate(String sql){


        try{


            Statement statement =
                    connection.createStatement();


            return statement.executeUpdate(sql);



        }catch(Exception e){


            throw new RuntimeException(
                    "Update failed",
                    e
            );

        }

    }


}
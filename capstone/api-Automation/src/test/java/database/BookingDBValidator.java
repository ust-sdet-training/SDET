package database;


import java.sql.ResultSet;


public class BookingDBValidator {


    public void insertBooking(
            String id,
            String journeyType,
            String inventoryId,
            String state
    ){


        String sql =
                """
                INSERT INTO bookings
                VALUES(
                '%s',
                '%s',
                '%s',
                '%s',
                true,
                null
                )
                """.formatted(
                        id,
                        journeyType,
                        inventoryId,
                        state
                );


        new QueryExecutor()
                .executeUpdate(sql);

    }




    public boolean bookingExists(String id){


        String sql =
                """
                SELECT *
                FROM bookings
                WHERE id='%s'
                """.formatted(id);



        try{


            ResultSet result =
                    new QueryExecutor()
                            .executeQuery(sql);



            return result.next();


        }catch(Exception e){

            throw new RuntimeException(e);

        }

    }



    public String getState(String id){


        String sql =
                """
                SELECT state
                FROM bookings
                WHERE id='%s'
                """.formatted(id);



        try{


            ResultSet result =
                    new QueryExecutor()
                            .executeQuery(sql);



            if(result.next()){

                return result.getString("state");

            }


            return null;


        }catch(Exception e){

            throw new RuntimeException(e);

        }


    }

}
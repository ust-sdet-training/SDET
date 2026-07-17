package com.tripstack.db;


import java.sql.*;


public class BookingDB {



    public static String getEmployee(String pnr)
            throws Exception{


        Connection con=DBUtils.connection();


        PreparedStatement ps=
                con.prepareStatement(

                        "select emp_id from bookings where pnr=?"

                );


        ps.setString(1,pnr);


        ResultSet rs=ps.executeQuery();


        rs.next();


        return rs.getString("emp_id");


    }


}
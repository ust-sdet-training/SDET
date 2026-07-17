package com.apitesting.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbHelper {

    public static int getFlightCount() throws Exception {

        String sql = "select count(*) from flights";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
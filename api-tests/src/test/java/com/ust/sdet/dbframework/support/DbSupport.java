package com.ust.sdet.dbframework.support;

import com.ust.sdet.config.DatabaseConfig;

import com.ust.sdet.dbframework.model.OrderRow;

import java.math.BigDecimal;

import java.sql.*;

public class DbSupport {

    private final DatabaseConfig config;


    public DbSupport(
            DatabaseConfig config
    ) {

        this.config = config;

    }


    public boolean isReachable()
            throws Exception {

        try (

                Connection connection =

                        openConnection()

        ) {

            return connection.isValid(
                    3
            );

        }

    }


    public OrderRow findOrder(
            long id
    )
            throws Exception {

        String sql = """

                SELECT

                id,

                order_number,

                status,

                total,

                user_id,

                created_at

                FROM orders

                WHERE id=?

                """;



        try (

                Connection connection =
                        openConnection();

                PreparedStatement ps =

                        connection.prepareStatement(
                                sql
                        )

        ) {

            ps.setLong(
                    1,
                    id
            );


            ResultSet rs =

                    ps.executeQuery();


            if (

                    !rs.next()

            ) {

                return null;

            }


            return new OrderRow(

                    rs.getLong(
                            "id"
                    ),

                    rs.getString(
                            "order_number"
                    ),

                    rs.getString(
                            "status"
                    ),

                    rs.getBigDecimal(
                            "total"
                    ),

                    rs.getString(
                            "user_id"
                    ),

                    rs.getTimestamp(
                                    "created_at"
                            )
                            .toInstant()

            );

        }

    }


    public BigDecimal totalForOrder(
            long id
    )
            throws Exception {

        OrderRow order =

                findOrder(
                        id
                );

        return order == null

                ?

                BigDecimal.ZERO

                :

                order.getTotal();

    }


    private Connection openConnection()
            throws Exception {

        return DriverManager.getConnection(

                config.jdbcUrl(),

                config.username(),

                config.password()

        );

    }

}
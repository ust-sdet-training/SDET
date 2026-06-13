package com.ust.sdet.dbframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ust.sdet.dbframework.support.DbSupport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

@JsonIgnoreProperties
public record OrderRow(long id,
                       String orderNumber,
                       String status,
                       BigDecimal total,
                       String userId,
                       Instant createdAt) {}


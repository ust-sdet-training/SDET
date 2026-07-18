package com.ust.sdet.api.tests.Day6_Fault;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.db.assertions.BookingDataAssertions;
import com.ust.sdet.api.db.queries.BookingQueries;
import com.ust.sdet.api.models.*;
import com.ust.sdet.api.services.BookingService;
import com.ust.sdet.api.services.FlightService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

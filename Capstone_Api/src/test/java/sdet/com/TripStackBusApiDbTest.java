package sdet.com;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class TripStackBusApiDbTest {

    private static final String BASE_URL = "https://tripstack.doomple.com";
    private static final LocalDate JOURNEY_DATE = LocalDate.now().plusDays(13);
    private static final String DATE = JOURNEY_DATE.format(DateTimeFormatter.ISO_LOCAL_DATE);
    private static final String FROM = "DEL";
    private static final String TO = "IXC";
    private static final String BUS_CODE = "BUS-DELIXC-01";
    private static final String SEAT = "L3";
    private static final String BUS_OPERATOR = "KPN Travels";
    private static final String AMOUNT = "₹598.50";
    private static final String PASSENGER_NAME = "Peggy";
    private static final String PASSENGER_EMAIL = "peggy@tripstack.test";
    private static final String PASSENGER_PHONE = "9876543210";

    @Test
    void shouldSearchBusAndCompleteBookingFlow() {
        Response loginResponse = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.URLENC)
                .formParam("email", "dave@tripstack.test")
                .formParam("password", "Password@123")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Map<String, String> sessionCookies = loginResponse.cookies();
        assertThat(sessionCookies).isNotEmpty();

        String resultsHtml = given()
                .baseUri(BASE_URL)
                .cookies(sessionCookies)
                .accept(ContentType.HTML)
                .when()
                .get("/buses/results?from=" + FROM + "&to=" + TO + "&date=" + DATE)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(resultsHtml).contains("Buses from DEL to IXC");
        assertThat(resultsHtml).contains(BUS_OPERATOR);
        assertThat(resultsHtml).contains("4 buses found");

        String passengerHtml = given()
                .baseUri(BASE_URL)
                .cookies(sessionCookies)
                .accept(ContentType.HTML)
                .queryParam("type", "bus")
                .queryParam("inventory", BUS_CODE)
                .queryParam("seats", SEAT)
                .queryParam("boardingPoint", "Delhi Airport Road")
                .queryParam("droppingPoint", "Chandigarh Main Bus Terminal")
                .when()
                .get("/book/passenger")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(passengerHtml).contains("Passenger details");
        assertThat(passengerHtml).contains("Continue to payment");

        String bookingHtml = given()
                .baseUri(BASE_URL)
                .cookies(sessionCookies)
                .contentType(ContentType.URLENC)
                .formParam("journeyType", "bus")
                .formParam("inventoryId", BUS_CODE)
                .formParam("seatIds", SEAT)
                .formParam("boardingPoint", "Delhi Airport Road")
                .formParam("droppingPoint", "Chandigarh Main Bus Terminal")
                .formParam("firstName_L3", PASSENGER_NAME)
                .formParam("lastName_L3", "Test")
                .formParam("passengerAge_L3", "30")
                .formParam("passengerGender_L3", "Female")
                .formParam("email", PASSENGER_EMAIL)
                .formParam("phone", PASSENGER_PHONE)
                .when()
                .post("/book/passenger")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(bookingHtml).contains("Payment");
        assertThat(bookingHtml).contains(AMOUNT);

        String bookingId = extractBookingId(bookingHtml);
        assertThat(bookingId).isNotBlank();

        String confirmationHtml = given()
                .baseUri(BASE_URL)
                .cookies(sessionCookies)
                .contentType(ContentType.URLENC)
                .formParam("cardName", PASSENGER_NAME)
                .formParam("cardNumber", "1234567890123456")
                .formParam("cardExpiry", "12/34")
                .formParam("cardCvv", "123")
                .when()
                .post("/book/payment/" + bookingId)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(confirmationHtml).contains("Booking confirmed");
        assertThat(confirmationHtml).contains("You're all set!");
        assertThat(confirmationHtml).contains(AMOUNT);
    }

    @Test
    void shouldValidateBookingRecordInDatabase() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:capstone_db;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS booking_record (id INT AUTO_INCREMENT PRIMARY KEY, passenger_name VARCHAR(255), passenger_email VARCHAR(255), route_from VARCHAR(10), route_to VARCHAR(10), bus_operator VARCHAR(255), payment_amount VARCHAR(20), journey_date VARCHAR(20))");
                statement.executeUpdate("DELETE FROM booking_record WHERE passenger_email = '" + PASSENGER_EMAIL + "'");
                statement.executeUpdate("INSERT INTO booking_record (passenger_name, passenger_email, route_from, route_to, bus_operator, payment_amount, journey_date) VALUES ('" + PASSENGER_NAME + "', '" + PASSENGER_EMAIL + "', '" + FROM + "', '" + TO + "', '" + BUS_OPERATOR + "', '" + AMOUNT + "', '" + DATE + "')");

                ResultSet rs = statement.executeQuery("SELECT passenger_name, passenger_email, route_from, route_to, bus_operator, payment_amount, journey_date FROM booking_record WHERE passenger_email = '" + PASSENGER_EMAIL + "'");
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("passenger_name")).isEqualTo(PASSENGER_NAME);
                assertThat(rs.getString("passenger_email")).isEqualTo(PASSENGER_EMAIL);
                assertThat(rs.getString("route_from")).isEqualTo(FROM);
                assertThat(rs.getString("route_to")).isEqualTo(TO);
                assertThat(rs.getString("bus_operator")).isEqualTo(BUS_OPERATOR);
                assertThat(rs.getString("payment_amount")).isEqualTo(AMOUNT);
                assertThat(rs.getString("journey_date")).isEqualTo(DATE);
            }
        }
    }

    private String extractBookingId(String html) {
        Matcher matcher = Pattern.compile("/book/payment/([^\"]+)").matcher(html);
        assertThat(matcher.find()).isTrue();
        return matcher.group(1);
    }
}

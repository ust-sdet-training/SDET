package models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record bookingModel(
String firstname,
String lastname,
String totalprice,
String depositpaid,
String additionalneeds
) {
}
package com.capstone.models;

public class BookingRequest {
  private final String route;
  private final String journey;
  private final String travelDate;
  private final String employeeId;

  public BookingRequest(String route, String journey, String travelDate, String employeeId) {
    this.route = route;
    this.journey = journey;
    this.travelDate = travelDate;
    this.employeeId = employeeId;
  }

  public String getRoute() {
    return route;
  }

  public String getJourney() {
    return journey;
  }

  public String getTravelDate() {
    return travelDate;
  }

  public String getEmployeeId() {
    return employeeId;
  }
}

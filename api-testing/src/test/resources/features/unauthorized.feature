@security
Feature: Login
  Scenario: Bob should see only his own bookings

    Given "bob" logs in
    When he requests his bookings
    Then he should receive 200 response
    And every booking should belong to employee "1002"

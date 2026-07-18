@security
Feature: Login
  Scenario: Bob should see only his own bookings
    Given "bob" logs in
    When he requests his bookings
    Then he should receive 200 response
    And every booking should belong to employee "1002"

  Scenario: Bob cannot cancel another employee's booking

    Given "dave" has a confirmed booking
    And "bob" logs in
    When Bob cancels Dave's booking
    Then he should receive 403 response
    And the error should be "CROSS_NAMESPACE"
    Then "dave" logs in
    And he cancelled the ticket


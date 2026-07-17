@smoke
Feature: Login

  Scenario: Bob logs into TripStack successfully

    Given "bob" has account
    When "bob" logs in
    Then he should receive 200 response
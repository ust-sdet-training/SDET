@api
@negative
Feature: payment failure
  Scenario: Bob not able to complete payment
    Given "bob" logs in
    When he wants to book a ticket
    Then he should receive 502 response
@smoke
@api
Feature: Bus search

  Scenario: Search returns matching bus

    Given "bob" has account
    When "bob" logs in
    Then he should receive 200 response
    When "bob" searches from "BOM" to "DEL" on "17-08-2026"
    Then the API should return "BOM" "DEL" "2026-08-17"
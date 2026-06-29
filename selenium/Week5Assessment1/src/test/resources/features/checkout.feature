Feature: Catalog checkout
  As a user, I want to search the buses,
  Select a bus to my cart,
  verify the details

Background:
  Given The searchpage page is open

  @happy
  Scenario: The bus is searched
    When I search for bus between "Chennai" and "Coimbatore" on "30-06-2026"


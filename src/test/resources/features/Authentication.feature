Feature: Authentication

  Background:
    Given the sdet retail app is open

    @negative
    Scenario: Login with Valid Credentials
      When I click sign in button in home page
      Then I wait for sign in to enable
      And I enter "email" and "password"
      When I click sign in button
      Then I can see sign out button in home page
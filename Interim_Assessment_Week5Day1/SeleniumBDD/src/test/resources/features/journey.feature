Feature: Product Search and Product Validation
  As a customer, I want to search for a product and validate its details before viewing the product page.

   Background:
      Given the amazon app is open

  @smoke @searchAndEvaluation
  Scenario: Searching Product and validating results
  Given I am on home page
  When I search for "wireless mouse"
  And I click Search
  Then I see search results page loads
  And I capture all displayed product names
  When I Select any product dynamically from the first page
  Then I validate product title is displayed
  And I validate product price is available
  And I validate product rating (if available)
  When I open the product
  Then I see product page title contains the product name "wireless mouse"


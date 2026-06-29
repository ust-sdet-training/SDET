Feature: Validate Filter and Sorting on Amazon

  Background:
    Given the user is on amazon website

    Scenario:Validate Filter and Sort Products
      When the user enters "headphones" in search box
      Then the user clicks search submit button
      And  the user applies brand filter "brand.name"
      And the user sorts products by "sort.method"
      Then verify the products are sorted by method
      And the user clicks on first product
      Then verify the product belongs to the brand "brand.name"




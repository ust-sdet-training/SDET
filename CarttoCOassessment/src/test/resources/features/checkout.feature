Feature: Catalog checkout
  As a shopper,I want to search the catalog,add a product to my cart,and complete checkout,so that I can buy what I came for - and
  the team can prove that journey works on every build.


  Background:
    Given The catalog is open


    @smoke
    Scenario: Buy a single product end to end
      When I search for a "headphone"
      And I add the first result to the cart
      Then the cart badge shows 1
      When I open the cart
      Then the cart has 1 line item
      When I place the order
      Then the order is confirmed

      @smoke
      Scenario: Cart badge reflects the number of items added
        When I search for a "headphone"
        And I add the first result to the cart
        Then the cart badge shows 1

       @regression
       Scenario Outline:Buy "<product>" end to end
         When I search for a "<product>"
         And I add the first result to the cart
         Then the cart badge shows <count>
         When I open the cart
         Then the cart has <count> line item
         When I place the order
         Then the order is confirmed

         Examples:
            | product   | count |
            | headphone | 1     |
            | shoes     | 1     |
            | lamp      | 1     |

         @regression
         Scenario: A fresh cart is empty
           When I open the cart
           Then the cart has 0 line items

         @negative
         Scenario: Demonstrate failure screenshot capture
           When I search for a "headphone"
           And I add the first result to the cart
           Then the cart badge shows 2

           @positive
           Scenario: Demonstrate failure screenshot capture
             When I search for a "headphone"
             And I add the first result to the cart
             Then the cart badge shows 1



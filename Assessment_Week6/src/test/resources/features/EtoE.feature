Feature: End to End Full Journey


Background:
  Given The User Logged in with Valid Credentials

@e2e @smoke
Scenario: End to End
 When I go to Catalog page and searchFor product "Lamp"
 Then I got the matched product for "Lamp"
 And Selected the Product
 When Added the Product "Lamp" to the Cart of quantity 1
 And Place the Order at Address "UST-Trivandrum,Kerala"
 Then The get api/orders returns PLACED and validated the totalpaisa
 Then The Database is Validated








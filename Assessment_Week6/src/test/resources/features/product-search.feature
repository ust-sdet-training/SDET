Feature: Product Search


Background:
  Given The User Go to Catalog Page

@smoke @ui @api
Scenario: Search a Single product
  When Searched for product "Lamp" and Select it
  Then Go to the matched product for "Lamp" and validated Response









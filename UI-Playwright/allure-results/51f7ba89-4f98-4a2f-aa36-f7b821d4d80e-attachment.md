# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: Flight-Booking.spec.ts >> Flight Booking Test
- Location: tests\Flight-Booking.spec.ts:5:5

# Error details

```
Error: locator.click: Test ended.
Call log:
  - waiting for locator('//button[@data-date=\'`${date}`\']')

```

# Test source

```ts
  1  | import { Locator, Page, expect } from "@playwright/test";
  2  | 
  3  | export class FlightSearchPage {
  4  | 
  5  |     constructor(readonly page: Page) { }
  6  | 
  7  |     private fromInputField = () : Locator => this.page.getByRole('combobox', { name: 'From' });
  8  |     private toInputField = () : Locator => this.page.getByRole('combobox', { name: 'To' });
  9  |     private inputFieldOption = (option: string) : Locator => this.page.getByRole('option', { name: `${option}` });
  10 |     private dateInputField = (date: string) : Locator => this.page.locator("//button[@data-date='`${date}`']");
  11 |     private searchButton = () : Locator => this.page.getByRole('button', { name: 'Search' });
  12 | 
  13 |     async verifyFlightSearchPageLoaded(){
  14 |         await expect(this.page).toHaveURL('/flights/search');
  15 |         await expect(this.fromInputField()).toBeVisible();
  16 |     }
  17 | 
  18 |     async searchFlight(from: string,fromOption: string, to: string, toOption: string, date: string) {
  19 |         await this.fromInputField().fill(from);
  20 |         await this.inputFieldOption(fromOption).click();
  21 |         await this.toInputField().fill(to);
  22 |         await this.inputFieldOption(toOption).click();
> 23 |         await this.dateInputField(date).click();
     |                                         ^ Error: locator.click: Test ended.
  24 |         await this.searchButton().click();
  25 |     }
  26 | 
  27 | }
```
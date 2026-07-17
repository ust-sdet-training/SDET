import { Page } from '@playwright/test';

export class Locators {
  constructor(private readonly page: Page) {}

  emailInput = (): Locator => this.page.getByRole('textbox', { name: 'Email' });
  passwordInput = (): Locator => this.page.getByRole('textbox', { name: 'Password' });
  signInBtn = ():Locator => this.page.locator('[type="submit"]');
  
  journeyType = (): Locator => this.page.locator('a[href="/buses/search"][role="tab"]').filter({ hasText:'Buses'}).first();
  searchFromInput = (): Locator => this.page.getByRole('combobox', { name: 'From' });
  searchToInput = (): Locator => this.page.getByRole('combobox', { name: 'To' });
  searchDateInput = (): Locator => this.page.getByRole('textbox', { name: 'Date' });
  searchButton = (): Locator => this.page.getByRole('button', { name: 'Search' });

  firstResult = (): Locator => this.page.locator('text=/Bus|AC|semi/i').first();
  selectButton = (): Locator => this.page.getByRole('button', { name:'Select Seats'}).last();

  seatButton = (): Locator => this.page.getByRole('button', {name: /available/i}).first();
  proceedButton = (): Locator => this.page.getByRole('button', {name: 'Continue to passenger details'});
  passengerFirstNameInput = (): Locator => this.page.getByLabel('First Name');
  passengerLastNameInput = (): Locator => this.page.getByLabel('Last Name');
  passengerAgeInput = (): Locator => this.page.getByRole('spinbutton', {name: 'Age'})
  passengerGender = (): Locator => this.page.getByRole('combobox', {name: 'Gender'});
  passengerEmailInput = (): Locator => this.page.getByRole('textbox',{name:'Email'});
  passengerPhoneNumberInput = (): Locator => this.page.getByRole('textbox',{name:'Phone number'});

  continuPayment = () : Locator => this.page.getByRole('button',{name:'Continue to payment'});

  NameCardInput = (): Locator => this.page.getByRole('textbox', { name: 'Name on card'});
  CardNumberInput = (): Locator => this.page.getByLabel('Card number');
  expiryInput = (): Locator => this.page.getByRole('textbox', { name: 'Expiry' });
  cvvInput = (): Locator => this.page.getByRole('textbox', { name: 'CVV' });
  payButton = (): Locator => this.page.getByRole('button',{ name:'Pay'});
}

import type { Locator } from '@playwright/test';


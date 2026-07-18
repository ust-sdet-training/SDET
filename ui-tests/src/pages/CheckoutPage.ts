import { Page } from '@playwright/test';
import { Locators } from '@locators/locators';

export class CheckoutPage {
  private readonly locators: Locators;

  constructor(private readonly page: Page) {
    this.locators = new Locators(page);
  }

  async selectSeat() {
    await this.locators.seatButton().click();
  }

  async proceedToDetails(){
    await this.locators.proceedButton().click();
  }

  async fillPassengerDetails(firstName: string, lastName: string, age: string, gender: string, email: string, phoneNumber: string) {
    await this.locators.passengerFirstNameInput().fill(firstName);
    await this.locators.passengerLastNameInput().fill(lastName);
    await this.locators.passengerAgeInput().pressSequentially(age);
    await this.locators.passengerGender().selectOption(gender);
    await this.locators.passengerEmailInput().fill(email);
    await this.locators.passengerPhoneNumberInput().pressSequentially(phoneNumber);
  }

  async continuePayment(){
    await this.locators.continuPayment().click();
  }

  async pay(nameCard: string, cardNumber: string, expiry: string, cvv: string) {
    await this.locators.NameCardInput().fill(nameCard);
    await this.locators.CardNumberInput().fill(cardNumber);
    await this.locators.expiryInput().pressSequentially(expiry);
    await this.locators.cvvInput().fill(cvv);
    await this.locators.payButton().click();
  }
}

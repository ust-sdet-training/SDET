import { Locator, Page } from "@playwright/test";

export class PassengerPage {
  readonly page: Page;
  readonly firstName: Locator;
  readonly lastName: Locator;
  readonly age: Locator;
  readonly gender: Locator;
  readonly email: Locator;
  readonly phone: Locator;
  readonly continueButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.firstName = page.getByRole("textbox", { name: /^First name/ }).first();
    this.lastName = page.getByRole("textbox", { name: /^Last name/ }).first();
    this.age = page.getByRole("spinbutton", { name: /^Age/ }).first();
    this.gender = page.getByLabel(/Gender/).first();
    this.email = page.getByRole("textbox", { name: "Email" });
    this.phone = page.getByRole("textbox", { name: "Phone number" });
    this.continueButton = page.getByRole("button", {
      name: "Continue to payment",
    });
  }

  async fillPassengerDetails() {
    await this.firstName.waitFor({ state: "visible", timeout: 10000 });
    await this.firstName.fill("xavier");
    await this.lastName.fill("Ds");
    await this.age.fill("22");
    await this.gender.selectOption("male");
    await this.email.fill("xavier@tripstack.test");
    await this.phone.fill("9876543456");
    await this.continueButton.click();
  }
}

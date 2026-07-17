import { Locator, Page } from "@playwright/test";

export class PassengerLocators {
  constructor(private page: Page) {}

  firstNameInput(): Locator {
    return this.page.getByRole("textbox", { name: /first name/i }).first();
  }

  lastNameInput(): Locator {
    return this.page.getByRole("textbox", { name: /last name/i }).first();
  }

  ageInput(): Locator {
    return this.page.getByRole("spinbutton", { name: /age/i }).first();
  }

  genderSelect(): Locator {
    return this.page.getByRole("combobox", { name: /gender/i }).first();
  }

  emailInput(): Locator {
    return this.page.getByRole("textbox", { name: /email/i }).first();
  }

  phoneInput(): Locator {
    return this.page
      .getByRole("textbox", { name: /phone number|phone/i })
      .first();
  }

  continueButton(): Locator {
    return this.page.getByRole("button", { name: /continue to payment|continue/i }).first();
  }
}

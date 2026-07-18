import { Locator, Page } from "@playwright/test";

export class PaymentLocators {
  constructor(private page: Page) {}

  nameOnCardInput(): Locator {
    return this.page
      .getByRole("textbox", { name: /name on card/i })
      .first();
  }

  cardNumberInput(): Locator {
    return this.page.getByRole("textbox", { name: /card number/i }).first();
  }

  expiryInput(): Locator {
    return this.page.getByRole("textbox", { name: /expiry/i }).first();
  }

  cvvInput(): Locator {
    return this.page.getByRole("textbox", { name: /cvv/i }).first();
  }

  payButton(): Locator {
    return this.page.getByRole("button", { name: /^Pay\s*₹/i }).first();
  }
}

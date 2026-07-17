import { Locator, Page } from "@playwright/test";

export class PaymentPage {
  readonly page: Page;
  readonly nameOnCard: Locator;
  readonly cardNumber: Locator;
  readonly expiry: Locator;
  readonly cvv: Locator;
  readonly payButton: Locator;
  readonly viewTripsButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.nameOnCard = page.getByRole("textbox", { name: "Name on card" });
    this.cardNumber = page.getByRole("textbox", { name: "Card number" });
    this.expiry = page.getByRole("textbox", { name: "Expiry" });
    this.cvv = page.getByRole("textbox", { name: "CVV" });
    this.payButton = page.getByRole("button", { name: /Pay ₹/ });
    this.viewTripsButton = page.getByRole("button", { name: "View my trips" });
  }

  async pay() {
    await this.nameOnCard.fill("juspay");
    await this.cardNumber.fill("1234 5678 9012 3456");
    await this.expiry.fill("12/26");
    await this.cvv.fill("1234");
    await this.payButton.click();
  }
}

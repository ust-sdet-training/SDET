import { Page } from "@playwright/test";

export class BookingPage {
  constructor(private readonly page: Page) {}

  async travellerDetails(
    seatNumber: string,
    email: string,
    phone: string
  ): Promise<void> {
    await this.page
      .getByRole("textbox", {
        name: `First name (seat ${seatNumber})`,
      })
      .fill("Bob");

    await this.page
      .getByRole("textbox", {
        name: `Last name (seat ${seatNumber})`,
      })
      .fill("Tedd");

    await this.page
      .getByRole("spinbutton", {
        name: `Age (seat ${seatNumber})`,
      })
      .fill("23");

    await this.page
      .getByLabel(`Gender (seat ${seatNumber})`)
      .selectOption("male");

    await this.page
      .getByRole("textbox", { name: "Email" })
      .fill(email);

    await this.page
      .getByRole("textbox", { name: "Phone number" })
      .fill(phone);

    await this.page
      .getByRole("button", { name: "Continue to payment" })
      .click();
  }
}
import { expect, type Page } from "@playwright/test";

export class PosPage {
  constructor(private readonly page: Page) {}

  async goToPosPage() {
    await this.page.goto("/pos");
  }

  async statusBanner(){
    return this.page.getByTestId("network-banner");
  }
}
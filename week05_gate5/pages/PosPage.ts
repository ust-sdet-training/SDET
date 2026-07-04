import { expect, type Page } from "@playwright/test";
export class PosPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto("/pos");
  }

  async getbanner()
  {
    return this.page.locator("[data-testid='network-banner']");
  }
}
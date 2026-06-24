import { expect, type Page } from "@playwright/test";
export class NykaaPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto("https://www.nykaa.com/");
  }
}
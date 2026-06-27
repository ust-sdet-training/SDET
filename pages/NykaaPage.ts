import { Page, Locator, expect } from '@playwright/test';

export class NykaaPage {
  readonly page: Page;
  readonly searchBox: Locator;
  readonly heading: Locator;
  readonly noResultTitle: Locator;

  constructor(page: Page) {
    this.page = page;

    this.searchBox = page.locator('[placeholder*="Search"]');
    this.heading = page.locator('h1');
    this.noResultTitle = page.locator('.title');
  }

  async open() {
    await this.page.goto('https://www.nykaa.com/', {
      waitUntil: 'domcontentloaded',
    });
  }

  async searchProduct(product: string) {
    await this.searchBox.fill(product);
    await this.page.keyboard.press('Enter');
  }

  async validatePositiveResult() {
    await expect(this.heading).toBeVisible();
    await expect(this.heading).toHaveAttribute('aria-label', /Lip Makeup/i);
  }

  async validateNegativeResult() {
    await expect(this.noResultTitle)
      .toHaveText(/Thanks for visiting our website/i);
  }
}
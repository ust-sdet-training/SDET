import { Page, expect } from '@playwright/test';
import { Locators } from '@locators/locators';

export class HomePage {
  private readonly locators: Locators;

  constructor(private readonly page: Page) {
    this.locators = new Locators(page);
  }

  async openHomePage() {
    await this.page.goto('/');
  }

  async searchJourney(from: string, to: string, date: string) {
    await this.locators.journeyType().click();
    await this.locators.searchFromInput().fill(from);
    await this.locators.searchToInput().fill(to);
    await this.locators.searchDateInput().pressSequentially(date);
    await this.locators.searchButton().click();
  }

  async selectFirstResult() {
    await this.page.waitForURL(/\/flights\/results|\/buses\/results/i);
    await expect(this.locators.firstResult()).toBeVisible();
    await this.locators.selectButton().click();
    }   
}

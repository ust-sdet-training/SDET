import type { Locator, Page } from '@playwright/test';
import type { Logger } from 'winston';

export class BasePage {
  constructor(protected readonly page: Page, protected readonly logger?: Logger) {}

  protected async waitForVisible(locator: Locator, timeout = 10_000) {
    await locator.waitFor({ state: 'visible', timeout });
    return locator;
  }
}

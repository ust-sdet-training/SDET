import { expect, type Locator, type Page } from '@playwright/test';

export class BusSearchPage {
  readonly searchForm: Locator;
  readonly from: Locator;
  readonly to: Locator;
  readonly date: Locator;
  readonly search: Locator;

  constructor(private readonly page: Page) {
    this.searchForm = page.getByRole('search', { name: 'Bus search' });
    this.from = page.getByRole('combobox', { name: 'From', exact: true });
    this.to = page.getByRole('combobox', { name: 'To', exact: true });
    this.date = page.getByLabel('Date of journey');
    this.search = page.getByRole('button', { name: 'Search buses' });
  }

  async goto(): Promise<void> {
    await this.page.goto('/buses/search', { waitUntil: 'domcontentloaded' });
    await expect(this.searchForm).toBeVisible({ timeout: 60_000 });
  }

  async chooseCity(input: Locator, code: string): Promise<void> {
    const listboxId = await input.getAttribute('aria-controls');
    if (!listboxId) throw new Error(`City combobox has no associated suggestions list: ${code}`);

    const listbox = this.page.locator(`[id="${listboxId}"]`);
    await input.focus();
    await input.fill(code);
    await expect(listbox).toBeVisible();
    await listbox.getByRole('option', { name: new RegExp(`\\b${code}\\b`) }).click();
  }

  async searchRoute(from: string, to: string, journeyDate: string): Promise<void> {
    await this.chooseCity(this.from, from);
    await this.chooseCity(this.to, to);
    await this.date.fill(journeyDate);
    await this.search.click();
    await this.page.waitForLoadState('domcontentloaded');
  }
}

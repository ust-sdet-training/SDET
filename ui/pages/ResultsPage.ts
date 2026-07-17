import { BasePage } from './BasePage';

export class ResultsPage extends BasePage {
  async selectBusByOperatorAndKind(operator: string, kind: string) {
    const card = this.page
      .locator('article.bus-card')
      .filter({ hasText: operator })
      .filter({ hasText: kind })
      .first();

    await this.waitForVisible(card);
    await card.getByRole('button', { name: /select seats/i }).click();
    this.logger?.info('Bus selected');
  }
}

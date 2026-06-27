import { test as base } from '@playwright/test';
import { NykaaPage } from '../pages/NykaaPage';

type Fixtures = {
  nykaa: NykaaPage;
};

export const test = base.extend<Fixtures>({
  nykaa: async ({ page }, use) => {
    const nykaa = new NykaaPage(page);
    await use(nykaa);
  },
});

export { expect } from '@playwright/test';
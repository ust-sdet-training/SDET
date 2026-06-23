import {test as base} from '@playwright/test';
import { HomePage } from '../pages/HomePage';

export const test = base.extend<{home: HomePage}>({
    home: async ({ page }, use) => {
        await use(new HomePage(page));
    }
});
export {expect} from '@playwright/test';
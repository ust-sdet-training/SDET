import { test as base, expect } from '@playwright/test';
import { ShopFlow } from '../flows/SHopFlow';

export const test = base.extend<{shop: ShopFlow}>({
    shop: async ({ page }, use) => {
        await use (new ShopFlow(page));
    }
})

export {expect} from '@playwright/test';
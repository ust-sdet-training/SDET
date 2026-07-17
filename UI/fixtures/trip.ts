import { test as base, expect } from '@playwright/test';
import { TripFlow } from '../flows/TripFlow';

export const test = base.extend<{trip: TripFlow}>({
    trip: async ({ page }, use) => {
        await use (new TripFlow(page));
    }
})

export {expect} from '@playwright/test';
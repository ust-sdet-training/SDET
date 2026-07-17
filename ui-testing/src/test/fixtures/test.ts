import { test as base, expect } from "./evidence";

import { BookFlow } from '../flows/BookFlow';

export const test = base.extend<{
    book: BookFlow}> ({
    book: async ({ page }, use) => {
        await use(new BookFlow(page));
    },
});

export {expect} from '@playwright/test';
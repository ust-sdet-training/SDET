import { test as base, expect } from '@playwright/test';
import { LoginFlow } from '../flows/LoginFlows';

export const test = base.extend<{login: LoginFlow}>({
    login: async ({ page }, use) => {
        await use (new LoginFlow(page));
    }
})

export {expect} from '@playwright/test';
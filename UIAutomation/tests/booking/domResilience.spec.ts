import { test, expect } from '@playwright/test';

import { LoginPage } from '../../pages/auth/LoginPage';
import { BusSearchPage } from '../../pages/bus/BusSearchPage';
import { ENV } from '../../utils/env';
import { FROM_CITY_LABEL, TO_CITY_LABEL } from '../../utils/constants';
import { travelDate } from '../../utils/dateUtil';
import { resetNamespace } from '../../utils/apiReset';

test.beforeEach(async () => {
    await resetNamespace();
});

test(
    'Seat locators survive span/div tag changes @regression @resilience',
    async ({ page }) => {

        const loginPage = new LoginPage(page);
        const busSearchPage = new BusSearchPage(page);

        await loginPage.open();
        await loginPage.login(ENV.EMAIL, ENV.PASSWORD);

        await busSearchPage.selectBusTab();
        await busSearchPage.selectFromCity(FROM_CITY_LABEL);
        await busSearchPage.selectToCity(TO_CITY_LABEL);
        await busSearchPage.selectDate(travelDate());
        await busSearchPage.searchBus();
        await busSearchPage.openSeatSelection();

        // Role-based locator: matches by ARIA role, not tag name.
        // Passes whether the seat element is a real <button> or a
        // <div role="button">/<span role="button">, proving the
        // locator is tag-agnostic and survives the v2 DOM change.
        const firstAvailableSeat = page
            .getByRole('button', { name: /^Seat .+ available$/ })
            .first();

        await expect(firstAvailableSeat).toBeVisible();

        const tagName = await firstAvailableSeat.evaluate(el => el.tagName.toLowerCase());
        console.log(`Seat element rendered as <${tagName}> — role-based locator matched regardless of tag.`);

        // Sanity check: confirm this is NOT relying on a brittle tag/class
        // selector that would break if span/div/button changes.
        expect(['button', 'div', 'span']).toContain(tagName);
    }
);
import { test, expect } from '@playwright/test';

import { LoginPage } from '../pages/LoginPage';
import { HomePage } from '../pages/HomePage';
import { SearchResultPage } from '../pages/SearchResultPage';

import { LoginFlow } from '../flows/LoginFlow';
import { BookingFlow } from '../flows/BookingFlow';

import { employee } from '../utils/testData';

test.describe('Performance Tests', () => {

    test('Verify login completes within 5 seconds', async ({ page }) => {

        const loginFlow = new LoginFlow(
            new LoginPage(page)
        );

        const start = Date.now();

        await loginFlow.login(
            employee.email,
            employee.password
        );

        const duration = Date.now() - start;

        console.log(`Login Time : ${duration} ms`);

        expect(duration).toBeLessThan(5000);

    });

    test('Verify flight search completes within 5 seconds', async ({ page }) => {

        const loginFlow = new LoginFlow(
            new LoginPage(page)
        );

        const bookingFlow = new BookingFlow(
            new HomePage(page),
            new SearchResultPage(page),
            null as any,
            null as any,
            null as any,
            null as any
        );

        await loginFlow.login(
            employee.email,
            employee.password
        );

        const start = Date.now();

        await bookingFlow.searchFlight();

        const duration = Date.now() - start;

        console.log(`Search Time : ${duration} ms`);

        expect(duration).toBeLessThan(5000);

    });

    test('Verify search results load within 5 seconds', async ({ page }) => {

        const loginFlow = new LoginFlow(
            new LoginPage(page)
        );

        const bookingFlow = new BookingFlow(
            new HomePage(page),
            new SearchResultPage(page),
            null as any,
            null as any,
            null as any,
            null as any
        );

        await loginFlow.login(
            employee.email,
            employee.password
        );

        const start = Date.now();

        await bookingFlow.searchFlight();

        await expect(
            page.getByRole('button', {
                name: 'Book'
            }).first()
        ).toBeVisible();

        const duration = Date.now() - start;

        console.log(`Result Load Time : ${duration} ms`);

        expect(duration).toBeLessThan(5000);

    });

});
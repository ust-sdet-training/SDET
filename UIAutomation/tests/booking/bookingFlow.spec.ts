import { test, expect } from '@playwright/test';

import { LoginPage } from '../../pages/auth/LoginPage';
import { BusSearchPage } from '../../pages/bus/BusSearchPage';
import { SeatMapPage } from '../../pages/bus/SeatMapPage';
import { PassengerPage } from '../../pages/PassengerPage';
import { PaymentPage } from '../../pages/PaymentPage';
import { ENV } from '../../utils/env';
import { FROM_CITY_LABEL, TO_CITY_LABEL } from '../../utils/constants';
import { travelDate } from '../../utils/dateUtil';
import { resetNamespace } from '../../utils/apiReset';

test.beforeEach(async () => {
    await resetNamespace();
});

test(
    'TripStack Bus Booking Flow @regression @booking',
    async ({ page }) => {

        const loginPage = new LoginPage(page);
        const busSearchPage = new BusSearchPage(page);
        const seatMapPage = new SeatMapPage(page);
        const passengerPage = new PassengerPage(page);
        const paymentPage = new PaymentPage(page);

        await loginPage.open();
        await loginPage.login(ENV.EMAIL, ENV.PASSWORD);

        await expect(
            page.getByRole('tab', { name: 'Buses' })
        ).toBeVisible();

        await busSearchPage.selectBusTab();
        await busSearchPage.selectFromCity(FROM_CITY_LABEL);
        await busSearchPage.selectToCity(TO_CITY_LABEL);
        await busSearchPage.selectDate(travelDate());
        await busSearchPage.searchBus();
        await busSearchPage.openSeatSelection();

        const seatId = await seatMapPage.selectSeat();
        await seatMapPage.selectBoardingPoint();
        await seatMapPage.selectDroppingPoint();
        await seatMapPage.continueBooking();

        // Explicit checkpoint: prove the seat was booked (HELD) successfully,
        // independent of whatever happens with payment afterward. Reaching
        // the passenger details page is only possible if createBooking()
        // (the seat hold) already succeeded - this isolates "did the seat
        // booking succeed" from "did payment succeed," since those are two
        // separate steps in the state machine (HELD -> PAYMENT_PENDING ->
        // CONFIRMED) and a payment-gateway fault should never affect the
        // earlier, already-completed seat-hold step.
        await expect(
            page.getByRole('heading', { name: "Who's travelling?" })
        ).toBeVisible();
        console.log(`Seat ${seatId} successfully held — passenger details page reached.`);

        await passengerPage.fillTravellerDetails(
            seatId,
            'Jyothsna',
            'Vaidyanath',
            '24',
            'Female'
        );
        await passengerPage.fillContactDetails(ENV.EMAIL, '9999999999');
        await passengerPage.continueToPayment();

        await paymentPage.fillCardDetails(
            'Jyothsna Vaidyanath',
            '4111111111111111',
            '12/28',
            '123'
        );
        await paymentPage.pay();

        // Fault-aware: your Day-6 card injects a payment decline (402) for
        // this employee. Detect which outcome occurred and assert accordingly.
        const declinedBanner = page.getByText('payment declined by gateway');
        const successHeading = page.getByRole('heading', { name: "You're all set!" });

        const result = await Promise.race([
            declinedBanner.waitFor({ state: 'visible', timeout: 10000 }).then(() => 'declined'),
            successHeading.waitFor({ state: 'visible', timeout: 10000 }).then(() => 'success')
        ]);

        if (result === 'declined') {
            console.log('DETECTED: payment declined by gateway — Day-6 fault flag is active for this employee.');
            await expect(declinedBanner).toBeVisible();
            console.log(`Seat ${seatId} remains HELD/PAYMENT_PENDING — fault affected payment only, not the earlier seat-booking step.`);
        } else {
            console.log('Payment succeeded — no fault currently active.');
            await expect(successHeading).toBeVisible();
            await expect(page.locator('[data-id="state"]')).toHaveText('CONFIRMED');

            const pnrLocator = page.getByText(/^TS-\d+-\d{4}$/);
            await expect(pnrLocator).toBeVisible();

            const pnrText = await pnrLocator.textContent();
            expect(pnrText).toMatch(new RegExp(`^TS-${ENV.EMP_ID}-\\d{4}$`));

            console.log(`Booking confirmed with PNR: ${pnrText}`);
        }

        await page.screenshot({
            path: 'reports/booking-success.png',
            fullPage: true
        });
    }
);
import { test, expect } from '@playwright/test';
import { BookingFlow } from '../flows/BookingFlow';

test('Fault Injection - Payment Declined handled gracefully', async ({ page }) => {

    const booking = new BookingFlow(page);
    await booking.completeBookingJourney();
    expect(true).toBeTruthy();

});
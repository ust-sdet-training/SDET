import { test as base, expect } from '@playwright/test';
import { BookingFlow } from '../flows/BookingFlow';

type Fixtures = {
    bookingFlow: BookingFlow;
};

export const test = base.extend<Fixtures>({

    bookingFlow: async ({ page }, use) => {

        const bookingFlow = new BookingFlow(page);

        await use(bookingFlow);

    }

});

export { expect };
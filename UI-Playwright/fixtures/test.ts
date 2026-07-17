import { test as base } from '@playwright/test';
import { FlightFlow } from '../flows/FlightFlow';

type MyFixtures = {
    tripStackFlights: FlightFlow
};

export const test = base.extend<MyFixtures>({
     tripStackFlights: async ({ page }, use) => {
         await use(new FlightFlow(page));
     }
});

export { expect } from '@playwright/test';
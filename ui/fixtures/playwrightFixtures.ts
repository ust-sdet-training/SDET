import base, { expect as baseExpect } from '@playwright/test';
import { createTestLogger, attachFailureLog } from '../logger';
import type { Logger } from 'winston';
import { LoginPage } from '../pages/LoginPage';
import { BusSearchPage } from '../pages/BusSearchPage';
import { ResultsPage } from '../pages/ResultsPage';
import { SeatMapPage } from '../pages/SeatMapPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { ConfirmationPage } from '../pages/ConfirmationPage';

type TripStackFixtures = {
  loginPage: LoginPage;
  busSearchPage: BusSearchPage;
  resultsPage: ResultsPage;
  seatMapPage: SeatMapPage;
  passengerPage: PassengerPage;
  paymentPage: PaymentPage;
  confirmationPage: ConfirmationPage;
  logger: Logger;
  correlationId: string;
};

export const test = base.extend<TripStackFixtures>({
  logger: async ({ page }, use, testInfo) => {
    const { logger, logFile, correlationId } = createTestLogger(testInfo);
    try {
      await page.setExtraHTTPHeaders({ 'x-correlation-id': correlationId });
    } catch (e) {
      // ignore if the runtime does not allow header injection
    }
    await use(logger);
    await attachFailureLog(testInfo, logFile);
  },
  correlationId: async ({ logger }, use) => {
    const correlationId = (logger as Logger & { defaultMeta?: Record<string, unknown> }).defaultMeta?.correlationId as string | undefined;
    await use(correlationId ?? 'unknown');
  },
  loginPage: async ({ page, logger }, use) => {
    await use(new LoginPage(page, logger));
  },
  busSearchPage: async ({ page, logger }, use) => {
    await use(new BusSearchPage(page, logger));
  },
  resultsPage: async ({ page, logger }, use) => {
    await use(new ResultsPage(page, logger));
  },
  seatMapPage: async ({ page, logger }, use) => {
    await use(new SeatMapPage(page, logger));
  },
  passengerPage: async ({ page, logger }, use) => {
    await use(new PassengerPage(page, logger));
  },
  paymentPage: async ({ page, logger }, use) => {
    await use(new PaymentPage(page, logger));
  },
  confirmationPage: async ({ page, logger }, use) => {
    await use(new ConfirmationPage(page, logger));
  },
});

export const expect = baseExpect;

export type { TripStackFixtures };

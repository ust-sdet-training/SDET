import { test as base, expect } from '@playwright/test';
import type { BookingData, CardDetails, Credentials, PassengerDetails } from '../types/booking';
import { capturePageSnapshot, writeDiagnostics } from '../utils/artifacts';
import { BookingConfirmationPage } from '../pages/bookingConfirmationPage';
import { FlightResultsPage } from '../pages/flightResultsPage';
import { FlightSearchPage } from '../pages/flightSearchPage';
import { LoginPage } from '../pages/loginPage';
import { MyTripsPage } from '../pages/myTripsPage';
import { PassengerPage } from '../pages/passengerPage';
import { PaymentPage } from '../pages/paymentPage';
import { SeatSelectionPage } from '../pages/seatSelectionPage';
import bookingDataJson from '../test-data/booking-data.json';
import { config } from '../utils/config';

const requiredSecretNames = ['TRIPSTACK_EMAIL', 'TRIPSTACK_PASSWORD'] as const;

const readRequiredSecret = (name: string): string => {
  const value = process.env[name]?.trim();

  if (!value) {
    throw new Error(`Missing required environment variable: ${name}. Set it in .env locally or as a CI secret.`);
  }

  return value;
};

const getRuntimeSecrets = () => ({
  credentials: {
    email: readRequiredSecret('TRIPSTACK_EMAIL'),
    password: readRequiredSecret('TRIPSTACK_PASSWORD'),
  },
});

const hasRuntimeSecrets = (): boolean => requiredSecretNames.every((name) => Boolean(process.env[name]?.trim()));

export type AppPages = {
  login: LoginPage;
  flightSearch: FlightSearchPage;
  flightResults: FlightResultsPage;
  seatSelection: SeatSelectionPage;
  passenger: PassengerPage;
  payment: PaymentPage;
  confirmation: BookingConfirmationPage;
  myTrips: MyTripsPage;
};

const buildBookingData = (): BookingData => {
  const randomSuffix = `${Date.now().toString().slice(-4)}${Math.floor(Math.random() * 90 + 10)}`;

  return {
    ...bookingDataJson,
    employeeId: randomSuffix,
  };
};

const buildPassengerData = (): PassengerDetails => {
  const suffix = `${Date.now().toString().slice(-4)}${Math.floor(Math.random() * 90 + 10)}`;

  return {
    firstName: `${config.passengerFirstNamePrefix}${suffix}`,
    lastName: `${config.passengerLastNamePrefix}${suffix}`,
    age: String(25 + Math.floor(Math.random() * 10)),
    email: `traveler${suffix}@example.com`,
    phone: `+91${String(Date.now()).slice(-9)}`,
  };
};

const buildPaymentData = (): CardDetails => {
  const suffix = `${Date.now().toString().slice(-4)}${Math.floor(Math.random() * 90 + 10)}`;

  return {
    name: `${config.passengerFirstNamePrefix} ${config.passengerLastNamePrefix}${suffix}`,
    number: `4111${suffix.slice(0, 8)}1111`,
    expiry: '12/30',
    cvv: String(100 + Math.floor(Math.random() * 900)),
  };
};

export const test = base.extend<{
  bookingData: BookingData;
  user: Credentials;
  passengerData: PassengerDetails;
  paymentData: CardDetails;
  appPages: AppPages;
}>({
  page: async ({ page }, use) => {
    await use(page);
  },
  bookingData: async ({}, use) => use(buildBookingData()),
  user: async ({}, use) => {
    if (!hasRuntimeSecrets()) {
      throw new Error('Missing runtime secrets. Provide the required environment variables before running tests.');
    }
    return use(getRuntimeSecrets().credentials);
  },
  passengerData: async ({}, use) => {
    if (!hasRuntimeSecrets()) {
      throw new Error('Missing runtime secrets. Provide the required environment variables before running tests.');
    }
    return use(buildPassengerData());
  },
  paymentData: async ({}, use) => {
    if (!hasRuntimeSecrets()) {
      throw new Error('Missing runtime secrets. Provide the required environment variables before running tests.');
    }
    return use(buildPaymentData());
  },
  appPages: async ({ page }, use) => {
    await use({
      login: new LoginPage(page),
      flightSearch: new FlightSearchPage(page),
      flightResults: new FlightResultsPage(page),
      seatSelection: new SeatSelectionPage(page),
      passenger: new PassengerPage(page),
      payment: new PaymentPage(page),
      confirmation: new BookingConfirmationPage(page),
      myTrips: new MyTripsPage(page),
    });
  },
});

base.beforeEach(async ({ page }, testInfo) => {
  const startedAt = new Date().toISOString();
  await writeDiagnostics(
    testInfo,
    `Test: ${testInfo.title}\nStatus: started\nStarted at: ${startedAt}\nBase URL: ${config.baseURL}\n`,
  );
});

base.afterEach(async ({ page }, testInfo) => {
  const finishedAt = new Date().toISOString();
  const status = testInfo.status === 'passed' ? 'passed' : testInfo.status === 'failed' ? 'failed' : testInfo.status;
  const summary = `Test: ${testInfo.title}\nStatus: ${status}\nFinished at: ${finishedAt}\nBase URL: ${config.baseURL}\n`;
  await writeDiagnostics(testInfo, summary);
  await capturePageSnapshot(page, testInfo);
});

export { expect };

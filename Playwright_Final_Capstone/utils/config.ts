import dotenv from 'dotenv';
import path from 'node:path';

dotenv.config({ path: path.resolve(__dirname, '..', '.env') });

const hasRuntimeSecrets = (): boolean => Boolean(process.env.TRIPSTACK_EMAIL?.trim() && process.env.TRIPSTACK_PASSWORD?.trim());

export const config = {
  baseURL: process.env.BASE_URL || 'https://tripstack.doomple.com/',
  headless: process.env.HEADLESS !== 'false',
  slowMo: Number(process.env.SLOW_MO || 0),
  seatMapMaxRenderMs: Number(process.env.SEAT_MAP_MAX_RENDER_MS || 5000),
  paymentMaxMs: Number(process.env.PAYMENT_MAX_MS || 20000),
  passengerFirstNamePrefix: process.env.PASSENGER_FIRST_NAME_PREFIX || 'Test',
  passengerLastNamePrefix: process.env.PASSENGER_LAST_NAME_PREFIX || 'User',
  hasRuntimeSecrets,
};

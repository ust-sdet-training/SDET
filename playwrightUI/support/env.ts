type Credentials = { email: string; password: string };
type Passenger = { firstName: string; lastName: string; age: string; phone: string };
type PaymentCard = { name: string; number: string; expiry: string; cvv: string };

function required(name: string): string {
  const value = process.env[name];
  if (!value) throw new Error(`Missing required environment variable: ${name}`);
  return value;
}

function optional(name: string, fallback: string): string {
  return process.env[name] ?? fallback;
}

export const env = {
  credentials(): Credentials {
    return {
      email: required('TRIPSTACK_EMAIL'),
      password: required('TRIPSTACK_PASSWORD'),
    };
  },
  passenger(): Passenger {
    return {
      firstName: optional('TRIPSTACK_PASSENGER_FIRST_NAME', 'Test'),
      lastName: optional('TRIPSTACK_PASSENGER_LAST_NAME', 'Traveller'),
      age: optional('TRIPSTACK_PASSENGER_AGE', '30'),
      phone: optional('TRIPSTACK_PASSENGER_PHONE', '9999999999'),
    };
  },
  paymentCard(): PaymentCard {
    return {
      name: optional('TRIPSTACK_PAYMENT_CARD_NAME', 'Test Traveller'),
      number: optional('TRIPSTACK_PAYMENT_CARD_NUMBER', '4242424242424242'),
      expiry: optional('TRIPSTACK_PAYMENT_CARD_EXPIRY', '12/34'),
      cvv: optional('TRIPSTACK_PAYMENT_CARD_CVV', '123'),
    };
  },
  bookingEnabled(): boolean {
    return process.env.TRIPSTACK_RUN_BOOKING === 'true';
  },
  paymentFailureEnabled(): boolean {
    return process.env.TRIPSTACK_RUN_PAYMENT_FAILURE === 'true';
  },
};

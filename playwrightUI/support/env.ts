type Credentials = { email: string; password: string };
type Passenger = { firstName: string; lastName: string; age: string; phone: string };
type PaymentCard = { name: string; number: string; expiry: string; cvv: string };

function required(name: string): string {
  const value = process.env[name];
  if (!value) throw new Error(`Missing required environment variable: ${name}`);
  return value;
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
      firstName: required('TRIPSTACK_PASSENGER_FIRST_NAME'),
      lastName: required('TRIPSTACK_PASSENGER_LAST_NAME'),
      age: required('TRIPSTACK_PASSENGER_AGE'),
      phone: required('TRIPSTACK_PASSENGER_PHONE'),
    };
  },
  paymentCard(): PaymentCard {
    return {
      name: required('TRIPSTACK_PAYMENT_CARD_NAME'),
      number: required('TRIPSTACK_PAYMENT_CARD_NUMBER'),
      expiry: required('TRIPSTACK_PAYMENT_CARD_EXPIRY'),
      cvv: required('TRIPSTACK_PAYMENT_CARD_CVV'),
    };
  },
  bookingEnabled(): boolean {
    return process.env.TRIPSTACK_RUN_BOOKING === 'true';
  },
  paymentFailureEnabled(): boolean {
    return process.env.TRIPSTACK_RUN_PAYMENT_FAILURE === 'true';
  },
};

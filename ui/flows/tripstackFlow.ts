import type { Logger } from 'winston';
import type { LoginPage } from '../pages/LoginPage';
import type { BusSearchPage } from '../pages/BusSearchPage';
import type { ResultsPage } from '../pages/ResultsPage';
import type { SeatMapPage } from '../pages/SeatMapPage';
import type { PassengerPage } from '../pages/PassengerPage';
import type { PaymentPage } from '../pages/PaymentPage';
import type { ConfirmationPage } from '../pages/ConfirmationPage';

type BookingPages = {
  loginPage: LoginPage;
  busSearchPage: BusSearchPage;
  resultsPage: ResultsPage;
  seatMapPage: SeatMapPage;
  passengerPage: PassengerPage;
  paymentPage: PaymentPage;
  confirmationPage: ConfirmationPage;
};

type Credentials = { email: string; password: string };
type Journey = { from: string; to: string; date: string };
type Passenger = { firstName: string; lastName: string; age: string; gender: string; email: string; phone: string };
type Payment = { cardName: string; cardNumber: string; expiry: string; cvv: string };

export async function bookTrip(
  pages: BookingPages,
  credentials: Credentials,
  journey: Journey,
  passenger: Passenger,
  payment: Payment,
  empId: string,
  logger?: Logger
) {
  const { loginPage, busSearchPage, resultsPage, seatMapPage, passengerPage, paymentPage, confirmationPage } = pages;

  logger?.info('Booking flow started');
  await loginPage.goto();
  await loginPage.login(credentials.email, credentials.password);

  await busSearchPage.goto();
  await busSearchPage.search(journey.from, journey.to, journey.date);

  await resultsPage.selectBusByOperatorAndKind('KPN Travels', 'A/C Semi-Sleeper');
  const selectedSeatId = await seatMapPage.selectSeat('S4');
  await passengerPage.fillPassenger(selectedSeatId, passenger);
  await passengerPage.continueToPayment();
  await paymentPage.pay(payment);

  const pnr = await confirmationPage.getPNR();
  logger?.info('Booking flow completed', { empId, pnr });
  return pnr;
}

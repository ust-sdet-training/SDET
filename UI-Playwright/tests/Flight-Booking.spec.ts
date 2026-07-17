import { test} from '../fixtures/test';
import { Env } from '../utils/Env';
import { User } from '../test-data/user';
import { Logger } from '../utils/Logger';

test('Flight Booking Test', async ({ tripStackFlights }) => {
  Logger.info('Flight Booking Test Starts..');
  await tripStackFlights.openWebsite();
  await tripStackFlights.doLogin(Env.get('USER_EMAIL'), Env.get('USER_PASSWORD'));
  Logger.info('User Login Sucessful');
  await tripStackFlights.searchFlight(User.from,User.fromOption,User.to,User.toOption,User.date);
  await tripStackFlights.sortAndSelectFlight(User.sortBy,User.flightName);
  Logger.info(`${User.flightName} is available and selected succesfully`);
  await tripStackFlights.selectSeat(User.seat);
  Logger.info(`${User.seat} and selected succesfully`);
  await tripStackFlights.enterPassengerDetails(User.firstName,User.lastName,User.age,User.gender,Env.get('USER_EMAIL'), Env.get('USER_PHONE_NUMBER'));
  await tripStackFlights.enterPaymentDetails(Env.get('USER_NAME_ON_CARD'), Env.get('USER_CARD_NUMBER'),Env.get('USER_CARD_EXPIRY_DATE'), Env.get('USER_CARD_CVV'));
  await tripStackFlights.verifyTicketConfirmation(User.bookingStatus);
  Logger.info('Flight Booking Sucessful');
  Logger.info('Deleting Booked Flight Seat');
  await tripStackFlights.cleanBookings(User.cancelBookingStatus);
  Logger.info('Booked Flight Seat Deleted Successfully');
});


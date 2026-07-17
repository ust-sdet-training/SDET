import { test} from '../fixtures/test';
import { Env } from '../utils/Env';
import { User } from '../test-data/user';
import { Logger } from '../utils/Logger';

test('Flight Booking Test For Same From Location and To Location', async ({ tripStackFlights }) => {
  Logger.info('Flight Booking Test Starts..');
  await tripStackFlights.openWebsite();
  await tripStackFlights.doLogin(Env.get('USER_EMAIL'), Env.get('USER_PASSWORD'));
  Logger.info('User Login Sucessful');
  await tripStackFlights.searchFlight(User.from,User.fromOption,User.from,User.fromOption,User.date);
  await tripStackFlights.verifyNoFlightsForInvalidRouteSearch();
  Logger.info('No Flights Listed For Invalid Route Search');
});


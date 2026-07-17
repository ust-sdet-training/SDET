import {test,expect} from '../loggingFixtures/artifacts';

import userlogindata from '../test-data/UserLoginInfo.json'

import usercheckoutdata from '../test-data/UserCheckoutinfo.json'

import cartdetails from '../test-data/CardDetails.json'

import {LoginPage} from '../pages/LoginPage'

import { FlightHomePage } from '../pages/FlightHomePage';
import { FlightListPage } from '../pages/FlightListPage';
import { CabinSelectPage } from '../pages/CabinSelectPage';
import { CheckoutPage } from '../pages/CheckoutPage';

import {getPassword} from '../Config/Secrets'
import { PaymentPage } from '../pages/PaymentPage';
import { TicketBookFlow } from '../flows/TicketBookFlow';

test('Booking a Flight', async ({ page, log, evidence }) => {

  const user = "Carol"

  const departure = "BLR"

  const destination = "GOI"

  const Cabin = "Business"

  
  log.info("Loading the BaseUrl")

  await page.goto('/', {waitUntil: "domcontentloaded"});

  let loginpage = new LoginPage(page)


  log.info("Logging in the user",{ useremail: userlogindata[user].email })

  evidence.loggedInUser = userlogindata[user].email

  await loginpage.login(userlogindata[user].email, getPassword())

  log.info("Attaching the screenshot of the logged in user")

  await page.screenshot({ 
        path: 'screenshots/login-masked.png'
    });

  
    await test.info().attach("User Logged in", {
        path: "screenshots/login-masked.png",
        contentType: "image/png"
    });




  var flighthome : FlightHomePage = new FlightHomePage(page);

  await flighthome.setDepatureLocation(departure)
  await flighthome.setDestinationLocation(destination)


  var plus14Days = await flighthome.setDate(14)

  evidence.date = plus14Days;


  log.info("Searching for flights",{ 
    departure: departure, 
    destination: destination, 
    date: plus14Days 
  })

  

  await flighthome.search()

  var flightlist = new FlightListPage(page);

  flightlist.bookFirstFlight()

  var cabinselect = new CabinSelectPage(page);

  cabinselect.getFirstAvailableSeat()


  cabinselect.continue()

  
  log.info("Inputting Passenger details")

  var checkoutPage = new CheckoutPage(page);

  await checkoutPage.enterFirstName(usercheckoutdata[user].firstname);

  await checkoutPage.enterLastName(usercheckoutdata[user].lastname);

  await checkoutPage.enterAge(usercheckoutdata[user].age);

  await checkoutPage.enterEmail(usercheckoutdata[user].email);

  await checkoutPage.enterPhNo(usercheckoutdata[user].phoneno);


  await page.screenshot({ 
        path: 'screenshots/checkoutdetails.png'
    });

  
    await test.info().attach("Checkout Sample Details", {
        path: "screenshots/checkoutdetails.png",
        contentType: "image/png"
    });

  

  await checkoutPage.continue()

  log.info("Entering the card details")

  var paymentPage = new PaymentPage(page);

  await paymentPage.setCardDetails(cartdetails[user])

  await paymentPage.payPrice();

  const tripId = await paymentPage.getTripId();

  evidence.tripId = tripId;


  await page.goto('/my-trips', {waitUntil: "domcontentloaded"});

  await page.screenshot({ 
        path: 'screenshots/flight-booked.png'
    });

  
    await test.info().attach("Flight Booked Proof", {
        path: "screenshots/flight-booked.png",
        contentType: "image/png"
    });

    log.info("Flight Booked Successfully")


  log.info("Flight Cancellation Process Initiated")

  const tripCard = await page.locator(`[data-id="trip-${tripId}"]`);

  await expect(tripCard.locator('[data-id="state"]')).toHaveText(/Confirmed/i);

  await tripCard.getByRole('button',{name:/Cancel/i}).click()

  log.info("Flight Cancelled Successfully")

  await page.screenshot({ 
        path: 'screenshots/flight-cancelled.png'
    });

  
    await test.info().attach("Flight Booked Proof", {
        path: "screenshots/flight-cancelled.png",
        contentType: "image/png"
    });
  })
//////////////////////////////////////////////////////////////////////////////

    // test('Booking a Flight using Flow', async ({ page, log, evidence }) => {

    //   await page.goto('/', {waitUntil: "domcontentloaded"});

    //     var loginpage = new LoginPage(page)

    //     const user = "Carol"

    //     await loginpage.login(userlogindata[user].email, getPassword())

    //     let ticketbookingflow  = new TicketBookFlow(page,log,evidence,test,expect)

    //     ticketbookingflow.bookTicket(user,"GOI","PUN")
    // })
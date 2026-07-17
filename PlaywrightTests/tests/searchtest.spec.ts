import { test, expect } from '../fixture/index';
import { testUsers } from '../data/testUser';
import { util } from '../src/utils/util';
import { secrets } from '../src/utils/secrets';
import { testTripData } from '../data/testTripData';
import { testCard } from '../data/testCard';

test.describe("Test for the flow from ordering a bus",()=>{
    test("End To End Flow",async ({flow,log,evidence,page},testInfo)=>{
      await flow.start();
      log.info("The Home Page Opened");

      await expect(page).toHaveURL("https://tripstack.doomple.com/");
      log.info("The Home page url verified");
      
      await flow.clickLogin();
      log.info("Clicked on Login Page");

      await expect(page).toHaveURL("/login");
      log.info("Login page url verified");

      await flow.login(util.emailName(testUsers.user.name),secrets.getuserPassword(testUsers.user.name));
      log.info("Login sucessfully for the user",{username:util.emailName(testUsers.user.name),password:secrets.getuserPassword(testUsers.user.name)})
      evidence["user-details"] = {
                                  username: testUsers.user.name,
                                  status: "logged-in",
                                };
                                
      await flow.search(testTripData.trip1.from,testTripData.trip1.to,Number(testTripData.trip1.dateAdder));
      log.info(`Search for Bus`,{From:testTripData.trip1.from,To:testTripData.trip1.to,Date:Number(testTripData.trip1.dateAdder)});

      await flow.selectACUpperDeckBus();
      log.info("Selected AC Upper Deck BUS");

      await flow.addPassengerDetails(
        testUsers.user.name,
        testUsers.user.lastName,
        testUsers.user.age,
        util.emailName(testUsers.user.name),
        String(testUsers.user.phone)
      );
      log.info("Passenger details added successfully", {
        firstName: testUsers.user.name,
        lastName: testUsers.user.lastName,
        age: testUsers.user.age,
        email: util.emailName(testUsers.user.name),
        phoneNumber: String(testUsers.user.phone)
      });

      await flow.addPaymentDetails(
        testCard.card1.nameOnCard,
        testCard.card1.cardNumber,
        testCard.card1.expiry,
        testCard.card1.cvv
      );
      log.info("Payment details added successfully", {
        nameOnCard: testCard.card1.nameOnCard,
        cardNumber: testCard.card1.cardNumber,
        expiry: testCard.card1.expiry,
        cvv: testCard.card1.cvv
      });

      await flow.verifyConfirmationDetails();
      log.info("Confirmation page details are visible");

      // await search.gotoFirstProductDetailPage(testProduct.product1.name)
      // log.info("Go to teh product Detail page")

      // await expect(page).toHaveURL(`product/${testProduct.product1.sku}`);
      // log.info("Verified the Product Page is reaced using url",{url:testProduct.product1.sku});

      // await search.addToCart();
      // log.info("Product added to cart");

      // await expect(page).toHaveURL(`cart`);
      // log.info("Cart Page reached");


      // await search.checkOut();
      // log.info("Product added to cart");

      // await expect(page).toHaveURL(`checkout`);
      // log.info("checkout Page reached");

      // await search.addAddress(testUsers.user.address);
      // log.info("Product added to cart");

      // await search.placeOrder();
      // log.info("Product added to cart");

      // await search.getOrderByAPI(testProduct.product1.sku);
      // log.info("Product SKU is there",{SKU:testProduct.product1.sku});
    })
})
import {test} from '../fixtures/e2eFixture'
import {environment} from '../config/environment'

test.describe("SHOPKART", ()=>{

    test("Flight Search",async({login,flight, search, available,seat, pass, book,pay,confirm, log, evidence})=>{

        log.info("Flight Search test started");

        log.info("User Login with email and password");
        await login.login(environment.email,environment.password)
        evidence.login = { email: environment.email }; 

        log.info("Search Pgae for flight search");
        await flight.search()

         log.info("Searching DEL TO BLR Flights");
        await search.search('DEL', 'BLR', 'First', 'Friday, 17 July')
         evidence.search = {from: "DEL",
                            to: "BLR",
                            cabin: "First",
                            date: "Friday, 17 July",
                            };
        
        log.info("Checking the Flights available");
        await available.available()

        await seat.seat('1A')

        await pass.passengerselect(environment.firstname,environment.lastname,"23","Male",environment.email,environment.number)


        await confirm.confirm(environment.cardname,environment.cardnumber,environment.expiry,environment.cvv)


        log.info("FLight Search Complete");

    })

    test("Invalid Email",async({login, log, evidence})=>{

        log.info("User Login with invalid email and valid password");
       await login.invEmail(environment.invemail,environment.password)

    })

    test("Invalid Password ",async({login, log, evidence})=>{

        log.info("User Login with valid email and invalid password");
       await login.invPass(environment.email,environment.invpass)


    })
    

})  


 
       


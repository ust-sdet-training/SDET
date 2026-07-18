import {test} from "../fixture/TripStackFixture"
import {expect,Page} from "@playwright/test"
import {Env} from "../config/Env"
test("Bus ticket full flow",async({request,book,log,evidence})=>
{
    await book.login(Env.username,Env.password)
    log.info("Login for the Tripstack ")
    await book.search(Env.from,Env.to,Env.date)
    log.info("Search for the bus in Tripstack")
    await book.selectseat()
    log.info("Select the seat for the bus")
    await book.enterdetails(Env.firstname,Env.lastname,Env.age,Env.email,Env.ph_no)
    log.info("Enter the passender details")
    await book.payment(Env.firstname,Env.cardnumber,Env.exp,Env.cvv)
    
    log.info("Enter the cardnumber and do the payment")



});
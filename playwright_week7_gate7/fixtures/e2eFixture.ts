import {test as base} from '../evidence/artifact'
import {Flightflows} from '../flows/FlightFlows'
import {Loginflows} from '../flows/LoginFlow'
import {Searchflows} from '../flows/SearchFlightFlows'
import {Flightavb} from '../flows/FlightsavbFlow'
import {Seatflows} from '../flows/SeatFlows'
import {Passengerflows} from '../flows/PassengerFlows'
import {Bookflows} from '../flows/BookFLows'
import {Paymentflows} from '../flows/PaymentFlow'
import {Confirmflows} from '../flows/ConfirmFlows'

export const test =
base.extend
<{
    login : Loginflows
    flight : Flightflows
    search : Searchflows
    available : Flightavb
    seat : Seatflows
    pass : Passengerflows
    book : Bookflows
    pay : Paymentflows
    confirm : Confirmflows
}>
({
    flight : async({page}, use)=>{

        await use(new Flightflows(page))

    },

    login : async({page}, use)=>{

        await use(new Loginflows(page))

    },

    search: async({page}, use)=>{

        await use(new Searchflows(page))

    },

    available: async({page}, use)=>{

        await use(new Flightavb(page))

    },

     seat: async({page}, use)=>{

        await use(new Seatflows(page))

    },

    pass: async({page}, use)=>{

        await use(new Passengerflows(page))

    },

      book: async({page}, use)=>{

        await use(new Bookflows(page))

    },

     pay: async({page}, use)=>{

        await use(new Paymentflows(page))

    },

    confirm: async({page}, use)=>{

        await use(new Confirmflows(page))

    },


})

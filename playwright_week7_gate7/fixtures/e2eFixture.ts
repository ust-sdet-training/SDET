import {test as base} from '../evidence/artifact'
import {Flightflows} from '../flows/FlightFlows'
import {Loginflows} from '../flows/LoginFlow'
import {Searchflows} from '../flows/SearchFlightFlows'
import {Flightavb} from '../flows/FlightsavbFlow'

export const test =
base.extend
<{
    login : Loginflows
    flight : Flightflows
    search : Searchflows
    available : Flightavb
    
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

})

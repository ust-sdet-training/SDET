import { test, expect } from "../../fixtures/baseFixtures";


test.describe(
"Flight Search Performance",
()=>{


test(
"Flight results page should load within threshold",

async({page})=>{


const startTime = Date.now();


await page.goto(
"/flights/results?from=AMD&to=BOM"
);



await page
.getByText("Flights")
.waitFor({
    state:"visible"
});



const loadTime =
Date.now()-startTime;



console.log(
`Flight Results Load Time: ${loadTime} ms`
);



expect(loadTime)
.toBeLessThan(5000);



});


});
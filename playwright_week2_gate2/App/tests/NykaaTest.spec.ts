import{test } from  "../fixtures/NykaaFixture"


test("PASS FLOW", async({home})=>
{
    await home.HomeFlow("watches")
})

test("FAILURE FLOW", async({home})=>
{
    await home.NegativeHomeFlow("abcdejh")
})

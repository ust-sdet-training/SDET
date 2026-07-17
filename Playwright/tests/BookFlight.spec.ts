import { test } from "../fixtures/pageFixture";
import "../hooks/Hooks";

test("Search Flight",async ({bookFlow}, testInfo) => {
    await bookFlow.bookFlight(testInfo);
    }
);

test("Search without city", async ({negativeFlow}, testInfo) => {
    await negativeFlow.bookFlight(testInfo);
});
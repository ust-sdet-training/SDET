import { test, expect } from "../fixture";

test.describe("Test", () => {
    test("Home test", async({ home }) => {
        await home.check("perfumes");
    });
});
import { test } from "../fixtures/baseFixture";
import { Users } from "../data/Users";

test.describe("Login", () => {

    test("User should login successfully", async ({ loginPage }) => {

        await loginPage.open();

        await loginPage.verifyLoginPage();

        await loginPage.login(
            Users.username,
            Users.password
        );

    });

});
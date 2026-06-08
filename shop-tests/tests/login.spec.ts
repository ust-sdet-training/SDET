import { test } from '../fixtures/login';
import { testUsers } from '../fixtures/testUsers';

test.describe("Login validation checks", () => {
    test("login with valid credentials", async ({ login }) => {
        await login.validLogin(testUsers.validcustomer.email, testUsers.validcustomer.password);
    });
    test("login with invalid credentials", async ({ login }) => {
        await login.invalidLogin(testUsers.invalidcustomer.email, testUsers.invalidcustomer.password);
    });
    test("login with locked credentials", async ({ login }) => {
        await login.lockedLogin(testUsers.lockedcustomer.email, testUsers.lockedcustomer.password);
    });
});
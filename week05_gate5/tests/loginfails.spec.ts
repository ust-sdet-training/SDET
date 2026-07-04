 import { expect, test } from "../fixtures/app.fixture";
import { testUsers } from "../testData/testUsers";
 test("Flacky in login", async ({page,evidence,log}) => {
    await page.goto("/login");
    const loginForm = page.getByRole("form", { name: "Login" });
    await loginForm.getByLabel("Email").fill(testUsers.customer.email);
    await loginForm.getByLabel("Password").fill(testUsers.customer.password);
    await loginForm.getByLabel("Remember me").check();
    await loginForm.getByLabel("Country").selectOption({ label: "India" },{timeout:200});//timeout is given here to give explicit wait
     loginForm.getByRole("button", { name: "Sign in" }).click();//here the await is removed to make it flacky
    evidence.login=loginForm
    log.error("flacky error")
    await expect(page).toHaveURL(/\/home$/);
    page.waitForTimeout(100)
     expect(
      page.getByRole("heading", { name: `Welcome, ${testUsers.customer.displayName}` })
    ).toBeVisible();
    await expect(page.getByRole("button", { name: "Sign out" })).toBeVisible();
  });
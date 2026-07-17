import { test, expect } from "../fixtures/evidence";
import { FlightSearchFlow } from "../flow/FlightSearchFlow";
import { AppLogger, logger } from "../src/logger";
import dotenv from "dotenv";

dotenv.config();

test("Search Bus", async ({flow,evidence}, testInfo) => {
  await flow.loginUser(process.env.user ?? "", process.env.password ?? "");
  await flow.searchAndBook("https://tripstack.doomple.com/");
  
});
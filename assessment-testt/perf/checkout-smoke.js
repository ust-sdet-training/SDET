import http from "k6/http";
import { check, group } from "k6";
import { Counter } from "k6/metrics";

const failed = new Counter("failed_requests");

export default function () {
  const base = __ENV.BASE_URL || "https://api.tripstack.doomple.com";
  const email = __ENV.API_USER_EMAIL;
  const password = __ENV.API_USER_PASSWORD;

  // login to obtain token
  const loginRes = http.post(
    `${base}/api/auth/login`,
    JSON.stringify({ email: email, password: password }),
    {
      headers: { "Content-Type": "application/json" },
    },
  );

  const successLogin = check(loginRes, {
    "login succeeded": (r) => r.status === 200 && r.json("token"),
  });
  if (!successLogin) {
    failed.add(1);
    return;
  }
  const token = loginRes.json("token");

  const bookingRes = http.get(`${base}/api/health/ping`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  const ok = check(bookingRes, { "endpoint OK": (r) => r.status === 200 });
  if (!ok) failed.add(1);
}

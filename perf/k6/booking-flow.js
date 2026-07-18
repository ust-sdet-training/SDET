import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [{ duration: '20s', target: 3 }],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1200']
  }
};

export default function () {
  const res = http.post(`${__ENV.BASE_URL || 'https://api.tripstack.doomple.com'}/api/bookings`, JSON.stringify({ route: 'GOI->PUN', journey: 'Bus AC-semi deck', travelDate: '28/07/2026', employeeId: 'E12' }), {
    headers: { 'Content-Type': 'application/json' }
  });
  check(res, { 'booking accepted': (r) => r.status === 200 || r.status === 201 });
  sleep(1);
}

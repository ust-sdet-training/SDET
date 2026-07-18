import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [{ duration: '20s', target: 5 }],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<800']
  }
};

export default function () {
  const res = http.get(`${__ENV.BASE_URL || 'https://api.tripstack.doomple.com'}/api/buses/search?from=GOI&to=PUN&date=28/07/2026`);
  check(res, { 'status is 200': (r) => r.status === 200 });
  sleep(1);
}

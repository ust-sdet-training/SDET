import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 2,
  duration: '10s',
};

export default function () {
  http.get('https://tripstack.doomple.com/api/buses?from=BLR&to=HYD&date=2026-08-01');
  sleep(1);
}

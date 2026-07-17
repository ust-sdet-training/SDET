import http from 'k6/http';
import { check } from 'k6';
import { config } from '../config/config.js';

export const options = {
    vus: config.users,
    duration: config.duration,
};

export default function () {

    const response = http.get(`${config.baseUrl}`);

    check(response, {
        'Status is 200': (r) => r.status === 200,
    });

}
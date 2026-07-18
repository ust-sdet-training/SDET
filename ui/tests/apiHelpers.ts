import type { APIRequestContext, APIResponse } from '@playwright/test';

const BASE_URL = process.env.PLAYWRIGHT_BASE_URL ?? 'https://tripstack.doomple.com';
const API_BASE = `${BASE_URL}/api`;

export async function authLogin(request: APIRequestContext, email: string, password: string, ttlSeconds?: number) {
  const data: Record<string, unknown> = { email, password };
  if (ttlSeconds !== undefined) {
    data.ttlSeconds = ttlSeconds;
  }

  return request.post(`${API_BASE}/auth/login`, {
    data,
    headers: { 'content-type': 'application/json' },
  });
}

export async function getMe(request: APIRequestContext, token: string) {
  return request.get(`${API_BASE}/auth/me`, {
    headers: { Authorization: `Bearer ${token}` },
  });
}

export async function resetNamespace(request: APIRequestContext, token: string) {
  return request.post(`${API_BASE}/reset`, {
    headers: { Authorization: `Bearer ${token}` },
  });
}

export async function getBookingByPnr(request: APIRequestContext, token: string, pnr: string) {
  return request.get(`${API_BASE}/bookings/${encodeURIComponent(pnr)}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
}

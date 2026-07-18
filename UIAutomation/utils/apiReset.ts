import { ENV } from './env';

export async function resetNamespace(): Promise<void> {
    const loginResponse = await fetch(`${ENV.BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: ENV.EMAIL, password: ENV.PASSWORD })
    });

    const loginText = await loginResponse.text();
    if (!loginResponse.ok) {
        console.error(`Login failed: ${loginResponse.status} — ${loginText.slice(0, 200)}`);
        throw new Error(`Login failed with status ${loginResponse.status}`);
    }

    const loginData = JSON.parse(loginText);
    const token = loginData.token;

    const resetResponse = await fetch(`${ENV.BASE_URL}/api/reset`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: '{}'
    });

    const resetText = await resetResponse.text();
    if (!resetResponse.ok) {
        console.warn(`Namespace reset returned ${resetResponse.status}: ${resetText.slice(0, 200)} — proceeding anyway.`);
        return;
    }

    const resetData = JSON.parse(resetText);
    console.log(`Namespace reset: emp=${resetData.emp}, purged=${resetData.purged}`);
}
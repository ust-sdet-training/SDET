import { ENV } from './env';

export async function resetNamespace(): Promise<void> {
    const loginResponse = await fetch(`${ENV.BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: ENV.EMAIL, password: ENV.PASSWORD })
    });
    const loginData = await loginResponse.json();
    const token = loginData.token;

    const resetResponse = await fetch(`${ENV.BASE_URL}/api/reset`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: '{}'
    });

    if (!resetResponse.ok) {
        console.warn(`Namespace reset returned ${resetResponse.status} — proceeding anyway.`);
    } else {
        const resetData = await resetResponse.json();
        console.log(`Namespace reset: emp=${resetData.emp}, purged=${resetData.purged}`);
    }
}
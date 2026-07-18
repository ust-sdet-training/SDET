export class Logger {

    static info(message: string) {
        console.log(`[INFO] ${new Date().toLocaleTimeString()} - ${message}`);
    }

    static success(message: string) {
        console.log(`[PASS] ${new Date().toLocaleTimeString()} - ${message}`);
    }

    static error(message: string) {
        console.log(`[FAIL] ${new Date().toLocaleTimeString()} - ${message}`);
    }
}
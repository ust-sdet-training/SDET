export class Logger {

    static info(message: string) {

        console.log(`[INFO] ${message}`);

    }

    static success(message: string) {

        console.log(`[PASS] ${message}`);

    }

    static error(message: string) {

        console.log(`[FAIL] ${message}`);

    }

}
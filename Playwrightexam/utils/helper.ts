export class Helper {

    static async wait(seconds: number) {
        return new Promise(resolve => setTimeout(resolve, seconds * 1000));
    }

}
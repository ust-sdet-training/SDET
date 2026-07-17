export class RandomHelper {

    static number(max: number = 9999): number {
        return Math.floor(Math.random() * max);
    }

    static email(): string {
        return `user${this.number()}@test.com`;
    }

}
export class DateUtil {

    static getTravelDate(days: number): string {

        const date = new Date();

        date.setDate(date.getDate() + days);

        return date.toISOString().split('T')[0];
    }

}
// helpers/DateHelper.ts

export class DateHelper {

    /**
     * Returns a Date object after adding the specified number of days.
     */
    static getFutureDate(days: number): Date {
        const date = new Date();
        date.setDate(date.getDate() + days);
        return date;
    }

    /**
     * Returns the day of the month.
     * Example: 23
     */
    static getDay(days: number): string {
        return this.getFutureDate(days).getDate().toString();
    }

    /**
     * Returns the month name.
     * Example: July
     */
    static getMonth(days: number): string {
        return this.getFutureDate(days).toLocaleString("en-US", {
            month: "long"
        });
    }

    /**
     * Returns the year.
     * Example: 2026
     */
    static getYear(days: number): string {
        return this.getFutureDate(days).getFullYear().toString();
    }

    /**
     * Returns formatted date.
     * Example: 23 July 2026
     */
    static getFormattedDate(days: number): string {
        const future = this.getFutureDate(days);

        const day = future.getDate();

        const month = future.toLocaleString("en-US", {
            month: "long"
        });

        const year = future.getFullYear();

        return `${day} ${month} ${year}`;
    }

}
/** Returns a new date offset by the requested number of days. */
export const dateAfterDays = (days: number, from = new Date()): Date => {
  const date = new Date(from);
  date.setDate(date.getDate() + days);
  return date;
};

/** Formats a date as YYYY-MM-DD for API requests or input[type=date]. */
export const toIsoDate = (date: Date): string => date.toISOString().slice(0, 10);

/** Matches TripStack's accessible calendar label, for example "Sunday 16 August". */
export const toTripStackCalendarLabel = (date: Date): string =>
  new Intl.DateTimeFormat('en-GB', {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
    timeZone: 'Asia/Kolkata',
  })
    .format(date)
    .replace(/, /g, ' ');

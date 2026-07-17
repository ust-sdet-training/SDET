export const getFutureDate = (
  daysToAdd: number,
  startDate: Date = new Date(),
): Date => {
  const futureDate = new Date(startDate);

  futureDate.setDate(
    futureDate.getDate() + daysToAdd,
  );

  return futureDate;
};

export const formatAsIsoDate = (
  date: Date,
): string => {
  return date.toISOString().split('T')[0];
};

export const formatForTripStackCalendar = (
  date: Date,
): string => {
  return new Intl.DateTimeFormat('en-GB', {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
    timeZone: 'Asia/Kolkata',
  })
    .format(date)
    .replace(/, /g, ' ');
};

// Backward-compatible names used by existing page objects.
export const dateAfterDays = getFutureDate;
export const toIsoDate = formatAsIsoDate;
export const toTripStackCalendarLabel = formatForTripStackCalendar;

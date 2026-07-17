export const journey = {
  employeeId: '1030',
  from: 'HYD',
  to: 'BOM',
  daysFromToday: 14,
} as const;

/** Returns YYYY-MM-DD in the business timezone, avoiding local-machine timezone drift. */
export function dateInIndiaAfter(days: number): string {
  const date = new Date();
  date.setUTCDate(date.getUTCDate() + days);
  const parts = new Intl.DateTimeFormat('en-CA', {
    timeZone: 'Asia/Kolkata', year: 'numeric', month: '2-digit', day: '2-digit',
  }).formatToParts(date);
  const value = Object.fromEntries(parts.map(({ type, value }) => [type, value]));
  return `${value.year}-${value.month}-${value.day}`;
}

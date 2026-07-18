import { DATE_OFFSET_DAYS } from './constants';

/**
 * Returns today + DATE_OFFSET_DAYS as YYYY-MM-DD,
 * matching the API project's ConfigManager.travelDate() logic.
 */
export function travelDate(): string {
    const d = new Date();
    d.setDate(d.getDate() + DATE_OFFSET_DAYS);
    return d.toISOString().split('T')[0];
}
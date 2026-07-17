/** Masks sensitive values before they are written to logs or reports. */
export const maskValue = (value: string, visibleCharacters = 4): string => {
  if (!value) return '';
  if (value.length <= visibleCharacters) return '*'.repeat(value.length);
  return `${'*'.repeat(value.length - visibleCharacters)}${value.slice(-visibleCharacters)}`;
};

export const maskSensitivePayload = (value: string): string => {
  if (!value) return '';
  return value.replace(/\S/g, '*');
};

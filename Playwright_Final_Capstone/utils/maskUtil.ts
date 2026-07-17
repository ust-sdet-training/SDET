export const maskValue = (value: string, visibleCharacters = 4): string => {
  if (!value) return '';

  const valueLength = value.length;

  if (valueLength <= visibleCharacters) {
    return '*'.repeat(valueLength);
  }

  const hiddenPart = '*'.repeat(valueLength - visibleCharacters);
  const visiblePart = value.slice(-visibleCharacters);

  return hiddenPart + visiblePart;
};

export const maskSensitivePayload = (value: string): string => {
  if (!value) return '';

  return '*'.repeat(value.length);
};
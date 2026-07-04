
export function correlationId(
  prefix: string
) {

  return `${prefix}-${Date.now()}`;
}


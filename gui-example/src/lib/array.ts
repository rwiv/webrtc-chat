export function reverse<T>(elems: T[] | undefined | null): T[] {
  if (elems === undefined || elems === null) return [];
  const result: T[] = [];
  for (let i = elems.length - 1; i >= 0; i--) {
    result.push(elems[i]);
  }
  return result;
}

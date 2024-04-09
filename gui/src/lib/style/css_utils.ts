export function getMargin(elem: Element | undefined | null): number | undefined {
  if (elem === undefined || elem === null) {
    return undefined;
  }
  const style = window.getComputedStyle(elem);
  const margin = parseFloat(style.margin);
  if (Number.isNaN(margin)) {
    return undefined;
  }
  return margin;
}

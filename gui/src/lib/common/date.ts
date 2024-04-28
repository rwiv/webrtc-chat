export function getPrettyDateString(dateString: string) {
  // const koreaTimeDiff = 9 * 60 * 60 * 1000 // 한국 시간은 GMT 시간보다 9시간 앞서 있다
  const koreaTimeDiff = 0;
  const kr = Date.parse(dateString) - koreaTimeDiff;
  const now = Date.now() - kr;
  const restSec = Math.round(now / 1000);
  if (restSec < 60) {
    return `${restSec}초 전`;
  } else {
    const restMinute = Math.round(now / 1000 / 60);
    return `${restMinute}분 전`;
  }
}
